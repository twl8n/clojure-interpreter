#!/usr/bin/env clojint.sh
(ns ex-sqlite
  (:require [clojure.tools.namespace.repl :as tnr]
         [clojure.repl :refer [doc]]
         [clojure.java.shell :as shell]
         [clojure.java.io]
         [clojure.java.jdbc :as jdbc]
         [clojure.string :as str]
         [hugsql.core :as hugsql]
         ))

;; not quite
(def x-dbspec-sqlite
  {:vendor "sqlite"
   :name "hdemo.db"
   })

;; nope
(def z-dbspec-sqlite
  {:dbtype "sqlite"
   :dbname "hdemo.db" ;; db name
   })

;; yes
(def dbspec-sqlite
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "hdemo.db"})


;; https://github.com/cemerick/pomegranate
;; (require '[cemerick.pomegranate :as pom])
;; (pom/add-classpath "/home/user/~.m2/....")

;; Print the classpath?
;; (println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))

;; Hugsql macros must be outside defn and come before any mention of functions that they will create at
;; runtime. Two functions will be created for each :name in full.sql. 

(hugsql/def-db-fns (clojure.java.io/as-file "full.sql"))

(hugsql/def-sqlvec-fns (clojure.java.io/as-file "full.sql"))

;; {:exit 2, :out "", :err "ls: cannot access hdemo.db: No such file or directory\n"}

(defn init []
  (let [dbfile (shell/sh "ls" (:subname dbspec-sqlite))]
    dbfile))

;; :insert-addr (insert-address conn {:city "Bayview" :street "110 Maple Street" :postal_code "93312"})
;; :select-ilike-foo(address-ilike-city conn {:city "foo"})
;; :select-ilike-bayview (address-ilike-city conn {:city "bayview"})

;; Create hdemo.db via the sqlite3 cli

;; This error:
;; java.lang.IllegalArgumentException: db-spec org.sqlite.SQLiteConnection@1d23ff23 is missing a required parameter, compiling:(/Users/twl/Sites/git_repos/clojure-interpreter/./example_sqlite_1.clj:68:1)
;; Fixed by turning (jdbc/get-connection dbspec-sqlite)
;; into {:connection (jdbc/get-connection dbspec-sqlite)}

(defn -main
  "Using clojure.jdbc. Run some code and def vars that you can inspect in the repl."
  []
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    ;; Connecting to a SQLite database has the side effect of creating an empty
    ;; database file if it did not already exist. Checking the connection is somewhat redunant for SQLite.
    (if (>= 0 (:count (check-empty conn)))
      (do
        (println "count: " (:count (check-empty conn)))
        (create-address conn)))
    (println (insert-address conn {:street "100 Maple St." :city "Pleasant Town" :postal_code "99999"}))
    (println (all-address conn))
    ;; (println {:insert (jdbc/execute conn ["insert into address (city) values ('foo')"])})
    ))

(-main)


