#!/usr/bin/env clojint.sh
(ns ex-sqlite
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.shell :as shell]
            [clojure.java.io]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as str]
            [clojure.pprint]
            [hugsql.core :as hugsql]))

(def dbspec-sqlite
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "hdemo.db"})

;; Hugsql macros must be outside defn and come before any mention of functions that they will create at
;; compile time (or is it run time?). Two functions will be created for each :name in full.sql. 

(hugsql/def-db-fns (clojure.java.io/as-file "full.sql")
  {:adapter (adapter/hugsql-adapter-clojure-jdbc)})
(hugsql/def-sqlvec-fns (clojure.java.io/as-file "full.sql"))

(defn -main
  "Create the address table if it doesn't exist, insert a row, read all the records."
  []
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    ;; Connecting to a SQLite database has the side effect of creating an empty database file if it did not
    ;; already exist. Checking the connection would be somewhat redunant for SQLite. Checking for an empty
    ;; database is necessary for this demo.
    (if (>= 0 (:count (check-empty conn)))
      (do
        (printf "Database contains %s tables/sequences.\n" (:count (check-empty conn)))
        (printf "Creating address table.\n")
        (create-address conn)))
    (printf "Rows inserted: %s\n" (insert-address conn {:street "100 Maple St." :city "Pleasant Town" :postal_code "99999"}))
    (printf "The address table:")
    (clojure.pprint/print-table (all-address conn))))

(-main)
