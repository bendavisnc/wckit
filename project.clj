(defproject wckit "0.1.0-SNAPSHOT"
  :description "A tool for generating word clouds that's a wrapper of kumo."
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [com.kennycason/kumo 1.8]]
  :main ^:skip-aot wckit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
