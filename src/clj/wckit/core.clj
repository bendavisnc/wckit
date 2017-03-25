(ns wckit.core
  (:import
    [com.kennycason.kumo CollisionMode]
    [wckit.addcore IWCKit]
    )

  (:require
    [wckit.wc-input :as wc-input]
    [wckit.wc-builder :as wc-builder]
    [clojure.java.io :as io]
    [common.helpers :refer [init-kumo-logging!]]
    [clojure.edn :as edn]
    )
  )

;(:gen-class
;  :methods [#^{:static true} [createNew [] IWCKit]])

(defrecord WCKitProto [
    input-data,
    limit,
    size,
    font-data,
    ^CollisionMode collision-mode
    ;collision-mode,
    background-color
  ]
  IWCKit
    (size [this, width, height]
      (assoc this :size [width, height]))
    (limit [this, v]
      (assoc this :limit v))
    (fontColor [this, c]
      (assoc-in this [:font-data :color] c))
    (fontStyle [this, s]
      (assoc-in this [:font-data :style] s))
    (fontSize [this, v] ; todo - cleanup
      (assoc-in  
        (assoc-in this [:font-data :max-size] v)
          [:font-data :min-size] v))
    (maxFontSize [this, v]
      (assoc-in this [:font-data :max-size] v))
    (minFontSize [this, v]
      (assoc-in this [:font-data :min-size] v))
    ;(collision-mode [this, ^CollisionMode m]
    (collisionMode [this, m]
      (assoc this :collision-mode m))
    (backgroundColor [this, c]
      (assoc this :background-color c))
    (input [this, args]
      (wc-input/input this args))
    (spitPng [this, filepath]
      (.writeToFile
        (wc-builder/build this) filepath))
    (fromEdn [this, filepath]
      (map->WCKitProto
        (merge
          this
          (edn/read-string
            (slurp (io/resource filepath))))))
    )

(def ^:private defaults
  {
    :input-data {}
    :size [800, 600]
    :limit 100
    :font-data {
        :min-size 10
        :max-size 50
        :color "white"
        :style "impact"
      }
    :collision-mode CollisionMode/PIXEL_PERFECT
    :background-color "black"
  })


;;
;;
;; Exposed helper methods...

(defn create-new []
  (map->WCKitProto defaults))

(defn createNew []
  (create-new))

(defn size [wck, width, height]
  (.size wck width height))

(defn limit [wck, v]
  (.limit wck v))

(defn font-color [wck, & c]
  (.fontColor wck c))

(defn font-style [wck, s]
  (do
    (println "hey")
    (.fontStyle wck s)))

(defn max-font-size [wck, s]
  (.maxFontSize wck s))

(defn min-font-size [wck, s]
  (.minFontSize wck s))

(defn collision-mode [wck, m]
  (.collisionMode wck m))

(defn background-color [wck, c]
  (.backgroundColor wck c))

(defn input [wck, args]
  (.input wck args))

(defn spit-png [wck, filepath]
  (.spitPng wck filepath))

(defn from-edn [wck, filepath]
  (.fromEdn wck filepath))



;(defn -main []
;  (println
;    "hey guys..."))

(init-kumo-logging!)



