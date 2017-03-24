(ns wckit.core
  (:import
    [com.kennycason.kumo CollisionMode]
    [wckit.addcore IWCKit]
    )

  (:require
    [wckit.wc-input :as wc-input]
    [wckit.wc-builder :as wc-builder]
    [clojure.java.io :as io]
    [common.helpers :refer [init-apache-logging!]]
    )
  (:gen-class)
  )

(init-apache-logging!) ; I said shut up, you!



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
    (fontcolor [this, c]
      (assoc-in this [:font-data :color] c))
    (fontstyle [this, s]
      (assoc-in this [:font-data :style] s))
    (fontsize [this, v] ; todo - cleanup
      (assoc-in  
        (assoc-in this [:font-data :max-size] v)
          [:font-data :min-size] v))
    (maxfontsize [this, v]
      (assoc-in this [:font-data :max-size] v))
    (minfontsize [this, v]
      (assoc-in this [:font-data :min-size] v))
    ;(collision-mode [this, ^CollisionMode m]
    (collisionmode [this, m]
      (assoc this :collision-mode m))
    (backgroundcolor [this, c]
      (assoc this :background-color c))
    ;(input [this, args]
    ;  (wc-input/input this args))
    (spitpng [this, filepath]
      (.writeToFile
        (wc-builder/build this) filepath))
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
 

(defn create-new []
  (map->WCKitProto defaults))

