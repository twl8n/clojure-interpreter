#!/usr/bin/env clojint.sh
(ns example_serial_1
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.namespace.repl :as tnr]))

;; Read data from an Arduino usb serial port.

;; http://pschwarz.bicycle.io/blog/2014/01/28/serial_access_via_clojure.html

;; https://playground.arduino.cc/Interfacing/LinuxTTY
;; Could read /dev/tty* directly after config via stty
;; stty -F /dev/ttyUSB0 cs8 115200 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts
;; echo "Hello Arduino" > /dev/ttyUSB0
;; tail -f /dev/ttyUSB0

(def dbspec-sqlite
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "serial_demo.db"})


(use 'serial.util)
(use 'serial.core)
;; Do this if you don't already know the port.
;; (list-ports)
(def port (open "/dev/tty.usbmodem1421"))

(defn test-db "Write a test record to the db" []
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    (jdbc/execute! conn ["insert into serial_data (value) values (?)" 321])))

(defn read-n-bytes [instream nn]
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    (loop [ival (.read instream) accum []]
      (if (> ival 0)
        (jdbc/execute! conn ["insert into serial_data (value) values (?)" ival]))
      (if (= nn (count accum))
        (do
          (unlisten! port)
          accum)
        (recur (.read instream) (conj accum ival))))))
  
(defn read-bytes [instream]
  (let [conn {:connection (jdbc/get-connection dbspec-sqlite)}]
    (prn "read-bytes")
    (loop [ival (.read instream)]
      (prn "ival:" ival)
      (when-not (= ival "0")
        (jdbc/execute! conn ["insert into serial_data (value) values (?)" ival]))
      (recur (.read instream)))))

(prn "starting to listen:")  
(listen! port #(println (read-bytes %)))
(prn "quitting")
;; (unlisten! port)
