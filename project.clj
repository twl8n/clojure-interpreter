(defproject clojint "0.1.0-SNAPSHOT"
  :description "Simple Clojure interpreter."
  :url "https://github.com/twl8n/clojure-interpreter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.layerware/hugsql "0.4.8"] ;; standard, use with standard clojure.java.jdbc
                 [com.layerware/hugsql-core "0.4.8"] ;; use with funcool/clojure.jdbc
                 [com.layerware/hugsql-adapter-clojure-jdbc "0.4.8"] ;; use with funcool/clojure.jdbc
                 ;; Don't use funcool/clojure.jdbc
                 ;; [funcool/clojure.jdbc "0.9.0"] ;; use with hugsql-core and hugsql-adapter-clojure-jdbc
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.codec "0.1.1"]
                 [org.xerial/sqlite-jdbc "3.21.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 ;; [clj-serial "2.0.4-SNAPSHOT"] ;; Doesn't use https, lein refuses to download.
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [joda-time "2.9.9"]
                 [com.cemerick/pomegranate "1.0.0" :exclusions [org.clojure/clojure]] ;; Dynamically load dependencies
                 [clojure.java-time "0.3.1"] ;; https://github.com/dm3/clojure.java-time
                 [clj-time "0.14.2"] ;; https://github.com/clj-time/clj-time
                 [ring "1.5.0"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [org.imgscalr/imgscalr-lib "4.2"]
                 [image-resizer "0.1.9"]]
  :main ^:skip-aot clojint.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
