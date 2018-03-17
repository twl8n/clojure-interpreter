#!/usr/bin/env clojint.sh
(ns ex-sqlite
  (:require [clojure.tools.namespace.repl :as tnr]
         [clojure.repl :refer [doc]]
         [clojure.java.shell :as shell]
         [clojure.java.io]
         [clojure.java.jdbc :as jdbc]
         [clojure.string :as str]
         [hugsql.core :as hugsql]))

(def dbspec-sqlite
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "hdemo.db"})

;; Hugsql macros must be outside defn and come before any mention of functions that they will create at
;; compile time (or is it run time?). Two functions will be created for each :name in full.sql. 

(hugsql/def-db-fns (clojure.java.io/as-file "full.sql"))
(hugsql/def-sqlvec-fns (clojure.java.io/as-file "full.sql"))

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
    (println (all-address conn))))

(-main)


