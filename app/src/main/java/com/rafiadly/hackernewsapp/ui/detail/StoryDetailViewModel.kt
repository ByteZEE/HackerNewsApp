package com.rafiadly.hackernewsapp.ui.detail

import androidx.lifecycle.*
import com.rafiadly.hackernewsapp.data.model.Comment
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.data.repository.StoryRepository
import com.rafiadly.hackernewsapp.utils.Resource
import kotlinx.coroutines.launch

class StoryDetailViewModel(
private val storyRepository: StoryRepository
):ViewModel() {

    private var _commentIds = mutableListOf<Int>()
    private var _listComment = MutableLiveData<List<Comment>>()
    val listComment: LiveData<List<Comment>> = _listComment

    private var _favStory = MutableLiveData<Story>()
    val favStory: LiveData<Story> = _favStory

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var _storyId = MutableLiveData<Int>()
    private var _story = _storyId.switchMap {
        storyRepository.getStoryDetail(it).asLiveData()
    }
    val story = _story

    fun setStoryId(id:Int){
        _storyId.postValue(id)
        loadComment()
    }

    fun setListComment(listComment:List<Int>){
        _commentIds.addAll(listComment)
        loadComment()
    }

    private fun loadComment() {
        viewModelScope.launch {
            _loading.postValue(true)
            val mListComment = arrayListOf<Comment>()
            _commentIds.forEach {
                storyRepository.getComment(it).collect{ comment ->
                    if (comment is Resource.Success){
                        mListComment.add(comment.data!!)
                    }
                }
            }
            _listComment.postValue(mListComment)
            _loading.postValue(false)
        }
    }

    private fun getFavStory() {
        _favStory.postValue(storyRepository.getFavourite())
    }

    fun setFavStory(story: Story){
        storyRepository.setFavourite(story)
    }

    init {
        getFavStory()
    }
}