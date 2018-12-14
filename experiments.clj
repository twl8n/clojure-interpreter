(reduce (fn [map [kk vv]] (assoc map kk (str "xx" vv))) {} [[:foo 1] [ :bar 2] [ :baz 33]])
(reduce (fn [map [kk vv]] (assoc map kk (str "xx" vv))) {} (seq {:foo 1 :bar 2 :baz 33}))
(reduce (fn [map [kk vv]] (assoc map kk (str "xx" vv))) {} {:foo 1 :bar 2 :baz 33})
(reduce-kv (fn [map kk vv] (assoc map kk (str "xx" vv))) {} {:foo 1 :bar 2 :baz 33})
;; => {:foo "xx1", :bar "xx2", :baz "xx33"}

(require '[clj-http.client :as client] :verbose)
(def get-list
  ["http://laudeman.com/r1100rt/piaa_r1100rt.html"
   "http://laudeman.com/sorghum/"
   "http://laudeman.com/bug.html"
   "http://laudeman.com/volkswagen/"
   "http://laudeman.com/hondavfr/"
   "http://laudeman.com/timeout.pl"])

(defn gands [url suffix]
  (doall (pmap (fn [xx]
                 (let [body (:body (client/get xx))
                       fname (format "%s-%s.html" (second (re-find #".*/(.*)/" xx)) suffix)]
                   (spit fname body)
                   fname)) [url])))

(def foo (client/get "http://laudeman.com/hondavfr/"
                     {:async? true}
                     ;; respond callback
                     (fn [response] response)
                     ;; raise callback
                     (fn [exception] (println "exception message is: " (.getMessage exception)) response)))



(defn gands [url suffix]
  (client/with-connection-pool {:timeout 5 :threads 4 :insecure? false :default-per-route 10 :throw-exceptions false}
    (doall (pmap (fn [xx]
                   (let [body (:body
                               (client/get
                                xx
                                {:async? true}
                                ;; respond callback
                                (fn [response] response)
                                ;; raise callback
                                (fn [exception] (println "exception message is: " (.getMessage exception)) exception)))
                         fname (format "%s-%s.html" (second (re-find #".*/(.*)/" xx)) suffix)]
                     (spit fname body)
                     fname)) [url]))))


(defn gands [url suffix]
  (doall (pmap (fn [xx]
                 (let [body (:body
                             (client/get
                              xx
                              {:async? true}
                              ;; respond callback
                              (fn [response] response)
                              ;; raise callback
                              (fn [exception] (println "exception message is: " (.getMessage exception)) exception)))
                       fname (format "%s-%s.html" (second (re-find #".*/(.*)/" xx)) suffix)]
                   (spit fname body)
                   fname)) [url])))




(def foo (do (doall (pmap #(do (gands % 1) (gands % 2)) get-list)) (mapv #(gands % 3) get-list)))


;; Maybe Alemic works?

;; Use boot instead, which has its own built-in dynamic module installer
;; https://github.com/boot-clj/boot
;; Linux users install boot via
;; sudo bash -c "cd /usr/local/bin && curl -fsSLo boot https://github.com/boot-clj/boot-bin/releases/download/latest/boot.sh && chmod 755 boot"
;; linuxbrew.sh install requires root password, or hackery to use sudo. Doesn't work so well if your linux box doesn't have a root password.

;; https://stackoverflow.com/questions/16409182/any-way-to-add-dependency-to-lein-project-without-repl-restart?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

;; Another example of pomegranate
;; https://gist.github.com/j1n3l0/2239353#file-gistfile1-txt-L10

;; Another example
;; https://stackoverflow.com/questions/18834808/what-is-the-easiest-way-to-load-a-clojure-library-all-the-time?rq=1&utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

;; maybe doesn't work with clojure 1.8.0
;; https://github.com/cemerick/pomegranate/issues/94

;; It would be nice to be able to dynamically load modules without building the interpreter. In theory this
;; is possible, and (in theory) the loaded modules are cached locally. I can't get it to work.

;; Does not work
;; (require '[clojure.data.codec.base64])
;; FileNotFoundException Could not locate clojure/data/codec/base64__init.class or clojure/data/codec/base64.clj on classpath.  clojure.lang.RT.load (RT.java:456)

(use '[cemerick.pomegranate :only (add-dependencies)])
(add-dependencies :coordinates '[[org.clojure/tools.namespace "0.2.11"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"clojars" "https://clojars.org/repo"}))
(add-dependencies :coordinates '[[org.clojure/data.codec "0.1.1"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"clojars" "https://clojars.org/repo"}))

[etaoin "0.2.8-SNAPSHOT"]
(add-dependencies :coordinates '[[etaoin "0.2.8-SNAPSHOT"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"central" "https://repo1.maven.org/maven2/"
                                       "clojars" "https://clojars.org/repo"}))
(add-dependencies :coordinates '[[org.clojure/data.codec "0.1.1"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"central" "https://repo1.maven.org/maven2/"}))

(add-dependencies :coordinates '[[org.clojure/data.codec "0.1.1"]])

;; org.clojure : data.codec : 0.1.1
;; http://repo1.maven.org/maven2/org/clojure/data.codec/0.1.1/

(require '[clojure.data.codec.base64] :verbose)
(require '(clojure.data.codec.base64))
;; => nil
(def foo (clojure.data.codec.base64/encode (into-array Byte/TYPE "this is a test")))
(prn "foo: " (apply str (map char (clojure.data.codec.base64/decode foo))))

;; Not only did this throw a compiler execption using the add-dependencies, but it would never have
;; worked because encode takes a byte-array. See above.
(def foo (clojure.data.codec.base64/encode "this is a test"))
;; => CompilerException java.lang.ClassNotFoundException: clojure.data.codec.base64, compiling: ...


(use '[cemerick.pomegranate :only (add-dependencies)])
(add-dependencies :coordinates '[[clj-http "2.3.0"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"central" "https://repo1.maven.org/maven2/"
                                        "clojars" "https://clojars.org/repo"}))
;; Wait for the deps to be retrieved...
;; {[commons-codec "1.10" :exclusions [[org.clojure/clojure]]] nil
 ;; [org.apache.httpcomponents/httpmime "4.5.2" :exclusions [[org.clojure/clojure]]] nil
 ;; [potemkin "0.4.3" :exclusions [[org.clojure/clojure]]] #{[clj-tuple "0.2.2"] [riddley "0.1.12"]}
 ;; [org.apache.httpcomponents/httpclient "4.5.2" :exclusions [[org.clojure/clojure]]] #{[commons-logging "1.2"]}
 ;; [org.apache.httpcomponents/httpcore "4.4.5" :exclusions [[org.clojure/clojure]]] nil
 ;; [commons-io "2.5" :exclusions [[org.clojure/clojure]]] nil
 ;; [clj-tuple "0.2.2"] nil
 ;; [slingshot "0.12.2" :exclusions [[org.clojure/clojure]]] nil
 ;; [clj-http "2.3.0"] #{[commons-codec "1.10" :exclusions [[org.clojure/clojure]]] [org.apache.httpcomponents/httpmime "4.5.2" :exclusions [[org.clojure/clojure]]] [potemkin "0.4.3" :exclusions [[org.clojure/clojure]]] [org.apache.httpcomponents/httpclient "4.5.2" :exclusions [[org.clojure/clojure]]] [org.apache.httpcomponents/httpcore "4.4.5" :exclusions [[org.clojure/clojure]]] [commons-io "2.5" :exclusions [[org.clojure/clojure]]] [slingshot "0.12.2" :exclusions [[org.clojure/clojure]]]}
 ;; [riddley "0.1.12"] nil
 ;; [commons-logging "1.2"] nil}

(require '[clj-http.client :as client] :verbose)
;; => FileNotFoundException Could not locate clj_http/client__init.class or clj_http/client.clj on classpath. Please check that namespaces with dashes use underscores in the Clojure file name.  clojure.lang.RT.load (RT.java:456)
(client/get "http://example.com/resources/id")

;; decorate printf to add flush

;; https://github.com/weavejester/decorate

(defmacro redef
  "Redefine an existing value, keeping the metadata intact."
  [name value]
  `(let [m# (meta #'~name)
         v# (def ~name ~value)]
     (alter-meta! v# merge m#)
     v#))

(defmacro decorate
  "Wrap a function in one or more decorators."
  [func & decorators]
  `(redef ~func (-> ~func ~@decorators)))

(defn wrap-printf [func]
  (fn [& args]
    (apply func args)
    (flush)))

;; (decorate printf wrap-printf)

(defn use-clojint-printf
  "If not for the :refer-clojure statement in ns, re-def-ing printf would cause a warning."
  []
  (def core-printf clojure.core/printf)
  (defn printf [fmt & args]
    (core-printf fmt args)
    (flush)))

(def mbody-function-literal
  `(do (def ~'core-printf clojure.core/printf)
       (def ~'printf #(do 
                        (apply ~'core-printf %1 %&)
                        (flush)))))

(def mbody
  `(do (def ~'core-printf clojure.core/printf)
       (def ~'printf (fn [~'fmt & ~'args]
                       (print (apply format ~'fmt ~'args))
                       (flush)))))
  
(defmacro frob-printf
  "If not for the :refer-clojure statement in ns, re-def-ing printf would cause a warning."
  []
  mbody)
