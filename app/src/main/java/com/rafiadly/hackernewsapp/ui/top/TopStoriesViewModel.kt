package com.rafiadly.hackernewsapp.ui.top

import androidx.lifecycle.*
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.data.repository.StoryRepository
import com.rafiadly.hackernewsapp.utils.Resource
import kotlinx.coroutines.launch

class TopStoriesViewModel(private val storyRepository: StoryRepository):ViewModel() {
    private var _topStoryIds: MutableLiveData<List<Int>> = MutableLiveData()

    private var _topStories: MutableLiveData<List<Story>> = MutableLiveData()
    val topStories: LiveData<List<Story>> = _topStories

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var _favStory = MutableLiveData<Story>()
    val favStory: LiveData<Story> = _favStory

    init {
        getTopStoryIds()
        getFavStory()
    }

    private fun getTopStoryIds(){
        viewModelScope.launch {
            _loading.postValue(true)
            storyRepository.getTopStoryIds().collect{
                _topStoryIds.value = if (it is Resource.Success) it.data else emptyList()
                loadStories()
            }
        }
    }

    private fun loadStories() {
        viewModelScope.launch {
            if (_topStoryIds.value!!.isNotEmpty()){
                _loading.postValue(true)
                val listStories = arrayListOf<Story>()
                _topStoryIds.value!!.take(20).forEach {
                    storyRepository.getStoryDetail(it).collect{ story ->
                        if (story is Resource.Success){
                            listStories.add(story.data!!)
                        }
                    }
                }
                _topStories.postValue(listStories)
                _loading.postValue(false)
            }
        }
    }

    fun getFavStory() {
        _favStory.postValue(storyRepository.getFavourite())
    }
}