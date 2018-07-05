#!/usr/bin/env clojint.sh
(ns ex-sqlite
  (:require [clojure.tools.namespace.repl :as tnr]
         [clojure.repl :refer [doc]]
         [clojure.java.shell :as shell]
         [clojure.java.io]
         [clojure.java.jdbc :as jdbc]
         [clojure.string :as str]))

(def dbspec-sqlite
  {:subprotocol "sqlite"
   :subname     "hdemo.db"})

;; If you see an error like this:
;; {:exit 2, :out "", :err "ls: cannot access hdemo.db: No such file or directory\n"}


;; Then run these commands in a Terminal to create hdemo.db via the sqlite3 cli:

;; cat | sqlite3 hdemo.db
;; create table address (
;;         id integer primary key autoincrement,
;;         street text,
;;         city text,
;;         postal_code text
;; );
;; ^C

(defn init []
  "Start thinking about how this script could initialize the db, if the db didn't yet exist."
  (let [dbfile (shell/sh "ls" (:subname dbspec-sqlite))]
    dbfile))

;; :insert-addr (insert-address conn {:city "Bayview" :street "110 Maple Street" :postal_code "93312"})
;; :select-ilike-foo(address-ilike-city conn {:city "foo"})
;; :select-ilike-bayview (address-ilike-city conn {:city "bayview"})

(defn -main
  "Using clojure.java.jdbc with inline SQL. The database must exist, and have been initialized."
  []
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    (println "Database connection check: " (jdbc/query conn ["SELECT 1"]))
    (println {:insert (jdbc/execute! conn ["insert into address (city) values ('foo')"])})))

(-main)


