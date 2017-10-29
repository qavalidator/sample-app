architecture(name: "T-View", prefix: "tview", reflexMLversion: "1.0") {

    excludes "java.lang.**", "java.util.**", "org.slf4j.**"

    component("App") {
        component("API") {
            api "de.qaware.qav.app.api.**"
        }

        component("Service") {
            api "de.qaware.qav.app.rest.**"
            uses "Spring"
        }

        component("Business") {
            api "de.qaware.qav.app.business.api.**"
            impl "de.qaware.qav.app.business.impl.**"
            uses "Spring"
        }

        component("Util") {
            api "de.qaware.qav.app.util.**"
        }

        component("Starter") {
            api "de.qaware.qav.app.AppApplication"
            uses "Spring"
        }
    }

    component("3rdParty") {
        component("Spring") { api "org.springframework.**" }
    }
}
