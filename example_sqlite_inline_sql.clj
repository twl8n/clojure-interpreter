#!/usr/bin/env clojint.sh
(ns ex-sqlite
  (:require [clojure.tools.namespace.repl :as tnr]
         [clojure.repl :refer [doc]]
         [clojure.java.shell :as shell]
         [clojure.java.io]
         [jdbc.core :as jdbc] ;; clojure.jdbc
         [clojure.string :as str]
         [hugsql.core :as hugsql]
         [hugsql.adapter.clojure-jdbc :as cj-adapter]))

(def dbspec-sqlite
  {:subprotocol "sqlite"
   :subname     "hdemo.db"})

;; {:exit 2, :out "", :err "ls: cannot access hdemo.db: No such file or directory\n"}

(defn init []
  (let [dbfile (shell/sh "ls" (:subname dbspec-sqlite))]
    dbfile))

;; :insert-addr (insert-address conn {:city "Bayview" :street "110 Maple Street" :postal_code "93312"})
;; :select-ilike-foo(address-ilike-city conn {:city "foo"})
;; :select-ilike-bayview (address-ilike-city conn {:city "bayview"})

;; Create hdemo.db via the sqlite3 cli
;; cat | sqlite3 hdemo.db
;; create table address (
;;         id integer primary key autoincrement,
;;         street text,
;;         city text,
;;         postal_code text
;; );
;; ^C

(defn -main
  "Using clojure.jdbc. Run some code and def vars that you can inspect in the repl."
  []
  (let [conn (jdbc/connection dbspec-sqlite)]
    (println "Database connection check: " (jdbc/fetch conn ["SELECT 1"]))
    (println {:insert (jdbc/execute conn ["insert into address (city) values ('foo')"])})))

(-main)


