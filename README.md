## AndMVP简介
An Android MVP Architecture Diagram Framwork.      
在MVP模式下，View和Model是完全分离没有任何直接关联的(比如你在View层中完全不需要导Model的包，也不应该去关联它们)。

## 使用 

#### 添加依赖配置  
  
```
dependencies {
    implementation 'com.utopia:AndMVP:1.0.2'
}
```  

#### 完整示例
```
非常简单，仅需要继承MVP框架封装好的父类即可，具体用法请参考demo。
```  

#### 框架说明
![Image text](https://raw.githubusercontent.com/freeutopia/AndMVP/main/images/uml.jpg)

## 开源协议
```
 Copyright (C) 2021, freeutopia
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ```
