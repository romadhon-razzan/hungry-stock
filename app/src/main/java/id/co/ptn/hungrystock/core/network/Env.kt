package id.co.ptn.hungrystock.core.network

enum class Env {
//    PRODUCTION {
//        override fun serviceUrl(): String {
//            return "https://hungrystock.com/api/"
//        }
//
//        override fun serviceSecretKey(): String {
//            return ""
//        }
//
//        override fun userKey(): String {
//            return "8000"
//        }
//
//        override fun webUrl(): String {
//            return "https://hungrystock.com/"
//        }
//
//        override fun debug(): Boolean {
//            return false
//        }
//
//    },
PRODUCTION {
    override fun serviceUrl(): String {
        return "https://hungrystock.com/api/v3/"
    }

    override fun serviceSecretKey(): String {
        return "AAACr_y89IrkU9DYfOD7o"
    }

    override fun userKey(): String {
        return "8000"
    }

    override fun webUrl(): String {
        return "https://hungrystock.com/"
    }

    override fun debug(): Boolean {
        return false
    }

},
    DEVELOPMENT {
        override fun serviceUrl(): String {
            return "https://hs.wintera.co.id/api/v3/"
        }

        override fun serviceSecretKey(): String {
            return "AAACr_y89IrkU9DYfOD7o"
        }

        override fun userKey(): String {
            return "8000"
        }

        override fun webUrl(): String {
            return "https://hs.wintera.co.id/"
        }

        override fun debug(): Boolean {
            return true
        }

    };

    abstract fun serviceUrl() : String
    abstract fun serviceSecretKey() : String
    abstract fun userKey() : String
    abstract fun webUrl() : String
    abstract fun debug() : Boolean
}