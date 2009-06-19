dataSource {
	pooled = true
    driverClassName = "org.postgresql.Driver"
	username = "username"
	password = ""
    dialect = org.hibernate.dialect.PostgreSQLDialect
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql://localhost:5432/urtstats_development"
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:postgresql://localhost:5432/urtstats_test"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:postgresql://localhost:5432/urtstats_production"
		}
	}
}