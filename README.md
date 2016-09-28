# Pre-work - *MyDayly*

**MyDayly** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Lakshmy Ramaswamy**

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Each item has a category associated with it so that the user can categorize it further. Idea here is to further allow filtering of the list. But I couldn't get that done due to time constraints.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/uYfrMmw.gifv' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

The biggest challenge I had was whwen my activity refused to return with my data after it completed. I wasnt able to find the reason when the onActivityResult didnt get triggered after my activity finished successfully. Even debug showed that the finish on my activity triggered correctly. Turned out that I was using the Result_OK instead of REQUEST_CODE and because Result_OK is negative, it didnt trigger the return to my main activity.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.