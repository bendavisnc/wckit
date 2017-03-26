(defproject wckit "0.1.0-SNAPSHOT"
  :description "A tool for generating word clouds that's a wrapper of kumo."
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-auto "0.1.3"]]
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [com.evocomputing/colors "1.0.3"]
    [com.kennycason/kumo "1.8"]]
  ;:main ^:skip-aot wckit.playground
  ;:main wckit.playground.core
  :main wckit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]
  :aot :all
  :auto {:default {
    :file-pattern #"\.(clj|cljs|cljx|cljc|edn)$"
    :paths ["resources"]
    }}
  )
