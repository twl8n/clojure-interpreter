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
(add-dependencies :coordinates '[[org.clojure/data.codec "0.1.1"]]
                  :repositories (merge cemerick.pomegranate.aether/maven-central
                                       {"central" "https://repo1.maven.org/maven2/"}))

(add-dependencies :coordinates '[[org.clojure/data.codec "0.1.1"]])

;; org.clojure : data.codec : 0.1.1
;; http://repo1.maven.org/maven2/org/clojure/data.codec/0.1.1/

(require '[clojure.data.codec.base64] :verbose)
(require '(clojure.data.codec.base64))
;; => nil
(def foo (clojure.data.codec.base64/encode "this is a test"))
;; => CompilerException java.lang.ClassNotFoundException: clojure.data.codec.base64, compiling: ...
(decode foo)

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
