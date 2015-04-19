```
   _____             _         _       
  / ____|           | |       | |      
 | (___  _ __   __ _| |_ _   _| | __ _ 
  \___ \| '_ \ / _` | __| | | | |/ _` |
  ____) | |_) | (_| | |_| |_| | | (_| |
 |_____/| .__/ \__,_|\__|\__,_|_|\__,_|
        | |                            
        |_|                            
```

# Search Functionnality

Our webapp alows you to search for recipes from any list of keywords.
You can do a search by entering your search terms in the top of the webpage and press `ENTER`

Currently the results are scraped from 3 different websites (`simplyrecipes.com`,`recipe.com`,`allrecipes.com`)
(3 entries for each websites are kept)

# Creating List of Recipes
    
If you discover interesting recipes through our search functionnality you can organize them and they will be saved for you later visit of our webapp.
You can rename those cookList on the fly.


# Previous searches
You really love some exotic plate with an unpronouciable name? Well if you forgot to save it in one of your cooklist when you searched that name 5min ago;
You can still click on "recent search link" and avoid the hassle of re-typing the dish name!

# Tech Side of things
Everything is currently done client-side in ScalaJS [Link to the project](http://www.scala-js.org/) and its jQuery library.
A enhancement would be to build up
