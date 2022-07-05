/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.mindorks.framework.mvvm.core.data.local.db

import com.mindorks.framework.mvvm.core.data.model.db.Option
import com.mindorks.framework.mvvm.core.data.model.db.Question
import com.mindorks.framework.mvvm.core.data.model.db.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by amitshekhar on 07/07/17.
 */
@Singleton
class AppDbHelper @Inject constructor(private val mAppDatabase: AppDatabase) : DbHelper {
    override val allQuestions: Observable<List<Question>>
        get() = mAppDatabase.questionDao().loadAll()
            .toObservable()
    override val allUsers: Observable<List<User>>
        get() = Observable.fromCallable { mAppDatabase.userDao().loadAll() }

    override fun getOptionsForQuestionId(questionId: Long): Observable<List<Option>> {
        return mAppDatabase.optionDao().loadAllByQuestionId(questionId)
            .toObservable()
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.userDao().insert(user)
            true
        }
    }

    override val isOptionEmpty: Observable<Boolean>
        get() = mAppDatabase.optionDao().loadAll()
            .flatMapObservable { options: List<Option> ->
                Observable.just(
                    options!!.isEmpty()
                )
            }
    override val isQuestionEmpty: Observable<Boolean>
        get() = mAppDatabase.questionDao().loadAll()
            .flatMapObservable { questions: List<Question> ->
                Observable.just(
                    questions!!.isEmpty()
                )
            }

    override fun saveOption(option: Option): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.optionDao().insert(option)
            true
        }
    }

    override fun saveOptionList(optionList: List<Option>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.optionDao().insertAll(optionList)
            true
        }
    }

    override fun saveQuestion(question: Question): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.questionDao().insert(question)
            true
        }
    }

    override fun saveQuestionList(questionList: List<Question>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.questionDao().insertAll(questionList)
            true
        }
    }
}