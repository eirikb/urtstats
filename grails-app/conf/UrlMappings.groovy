class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
            constzraints {
                // apply constraints here
            }
        }
      "/"(view:"/index")
	  "500"(view:'/error')
    }
}
