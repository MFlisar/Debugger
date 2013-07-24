Debugger
========

[Android] convenient debugger with automatic function name and row number in output

Output looks like following:

    Main:   
        TAG: WeekViewActivity:1080 updateWeek
        TEXT: Text
    Thread: 
        TAG: BuyHelper:48 ...onQueryInventoryFinished
        TEXT: TEXT
        
sometimes you may want to use a GLOBAL Tag (for example, on live development for sending a bug report with just your application messages)...
For this reason, you can use following two methods (when creating your app):

    Debugger.setAppTag("MyAppTag");
    Debugger.setMode(MODE.APP_TAG);
    
Afterwards, the debug output will look like following:

    Main:   
        TAG: MyAppTag
        TEXT: 2013.07.24 19:11:44 [WeekViewActivity:1080 updateWeek] Text
    Thread: 
        TAG: MyAppTag
        TEXT: 2013.07.24 19:11:38 [BuyHelper:48 ...onQueryInventoryFinished] Text

Usage
=====

just call one of the static `Debugger.d` or `Debugger.e` functions...

Like for example:

    Debugger.d("debug message");
    Debugger.e(exception);
    ...

License
=======

    Copyright 2013 Michael Flisar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
