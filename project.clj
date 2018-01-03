(defproject clojint "0.1.0-SNAPSHOT"
  :description "Simple Clojure interpreter."
  :url "https://github.com/twl8n/clojure-interpreter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.layerware/hugsql "0.4.8"] ;; use with clojure.java.jdbc
                 [com.layerware/hugsql-core "0.4.8"] ;; use with clojure.jdbc
                 [com.layerware/hugsql-adapter-clojure-jdbc "0.4.8"] ;; use with clojure.jdbc
                 [funcool/clojure.jdbc "0.9.0"] ;; use with hugsql-core and hugsql-adapter-clojure-jdbc
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.xerial/sqlite-jdbc "3.21.0"]
                 [org.postgresql/postgresql "42.1.4"]]
  :main ^:skip-aot clojint.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
