/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.utopia.mvp.model;

import com.utopia.mvp.listener.DataChangeListener;

/**
 * 用作双向绑定使用
 * Do Model-View two-way binding future use
 *
 */
public interface IModel {

    void setCallback(DataChangeListener listener);

    default void fetch(){};

    default void fetch(int indexPage , int pageSize){};


    void onDestory();

}
