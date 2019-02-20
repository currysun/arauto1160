package com.lombardrisk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

class Profiles {

    @Configuration
    @Profile("native")
    @PropertySource({"classpath:env/application-native-browser.properties"})
    static class NativeBrowser {
        // no-op
    }

    @Configuration
    @Profile("bdd-env")
    @PropertySource({"classpath:env/application-bdd-env.properties"})
    static class BddEnv {
        // no-op
    }

    @Configuration
    @Profile("smoke-env")
    @PropertySource({"classpath:env/application-smoke-env.properties"})
    static class SmokeEnv {
        // no-op
    }

    @Configuration
    @Profile("chrome-headless")
    @PropertySource({"classpath:env/application-chrome-headless.properties"})
    static class ChromeHeadless {
        // no-op
    }

    @Configuration
    @Profile("chromegrid")
    @PropertySource({"classpath:env/application-chromegrid.properties"})
    static class ChromeGrid {
        // no-op
    }

    @Configuration
    @Profile("chrome-linux")
    @PropertySource({"classpath:env/application-chrome-linux.properties"})
    static class ChromeLinux {
        // no-op
    }

    @Configuration
    @Profile("firefox")
    @PropertySource({"classpath:env/application-firefox.properties"})
    static class Firefox {
        // no-op
    }

    @Configuration
    @Profile("ie")
    @PropertySource({"classpath:env/application-ie.properties"})
    static class Ie {
        // no-op
    }

    @Configuration
    @Profile("firefox-headless")
    @PropertySource({"classpath:env/application-firefox-headless.properties"})
    static class FirefoxHeadless {
        // no-op
    }

    @Configuration
    @Profile("firefoxgrid")
    @PropertySource({"classpath:env/application-firefoxgrid.properties"})
    static class FirefoxGrid {
        // no-op
    }

    @Configuration
    @Profile("ie-headless")
    @PropertySource({"classpath:env/application-ie-headless.properties"})
    static class IeHeadless {
        // no-op
    }

    @Configuration
    @Profile("iegrid")
    @PropertySource({"classpath:env/application-iegrid.properties"})
    static class IeGrid {
        // no-op
    }

    @Configuration
    @Profile("m1-env")
    @PropertySource({"classpath:env/application-m1-env.properties"})
    static class M1Env {
        // no-op
    }

    @Configuration
    @Profile("m2-env")
    @PropertySource({"classpath:env/application-m2-env.properties"})
    static class M2Env {
        // no-op
    }

    @Configuration
    @Profile("m3-env")
    @PropertySource({"classpath:env/application-m3-env.properties"})
    static class M3Env {
        // no-op
    }

    @Configuration
    @Profile("r1-env")
    @PropertySource({"classpath:env/application-r1-env.properties"})
    static class R1Env {
        // no-op
    }

    @Configuration
    @Profile("r2-env")
    @PropertySource({"classpath:env/application-r2-env.properties"})
    static class R2Env {
        // no-op
    }

    @Configuration
    @Profile("r3-env")
    @PropertySource({"classpath:env/application-r3-env.properties"})
    static class R3Env {
        // no-op
    }

    @Configuration
    @Profile("firefox-linux")
    @PropertySource({"classpath:env/application-firefox-linux.properties"})
    static class FirefoxLinux {
        // no-op
    }

    @Configuration
    @Profile("ofsaa-download")
    @PropertySource({"classpath:env/application-ofsaa-download.properties"})
    static class OFSAADownload {
        // no-op
    }

    @Configuration
    @Profile("fcr-qa-cluster")
    @PropertySource({"classpath:env/application-fcr-qa-cluster.properties"})
    static class FCRTestEnv {
        // no-op
    }
}
