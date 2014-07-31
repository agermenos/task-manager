# JAX-RS To Do API

RESTful application to do basic CRUD and Search operations on a ToDo List.

Extract from email:
"... (if you accept your mission) you (will) create a simple JSON RESTful API using
Jersey that basically provides a backend for an hypothetical "Todo List" app.

 1) The todo items should have endpoints that provide the following functionalities:

 - Get
 - Delete
 - Save
 - Update
 - Mark as done/undone
 - Search

 Todo items have a title, a body, and a boolean value to check if it has been done or not.
 (this message will auto-destroy in 10 seconds)"

The API that accomplishes this has the following methods:

URL                             Http Method     Function
======================          ============    ===========
/services/todos/                POST            Inserts a title (can't repeat titles) +
/services/todos/:title          PUT             Updates the task with the given title +
/services/todos/:title          DELETE          Deletes a title
/services/todos/:title          GET             Get by Title
/services/todos/:title          PATCH           To mark/unmark a task
/services/todos/tap/:title      POST            To mark/unmark a task (if PATCH doesn't work on your browser)
/services/todos/                GET             Equivalent to a "get all titles"
/services/todos/search/:word    GET             Uses the search engine to search
/admin/todos/index              POST            Creates a new index in the database
/admin/phone/:phoneNumber       POST            Updates the phone number for the SMS messages (Needs to be an authorized one!)

* If you do a delete and then a search, the title will still be there. Not in the case of the Get by Title,
since the search goes to the indexes.
** Functions with a + need to have the task in json format:

        {
            "title" : "",
            "body" : "",
            "done" false
        }

Thanks
Mash Ape, for giving me the opportunity to build this service. I learned a lot with this, and more important, I had a lot of fun!!

Alejandro Germenos
7/30/2014