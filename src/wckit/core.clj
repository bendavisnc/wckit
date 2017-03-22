(ns wckit.core
  (:import
    (com.kennycason.kumo.nlp FrequencyAnalyzer)
    (com.kennycason.kumo WordCloud)
    (com.kennycason.kumo CollisionMode)
    (com.kennycason.kumo.font.scale LinearFontScalar)
    (java.awt Dimension)
    (java.awt Color))
  (:require
    [wckit.wc-input :as wc-input]
    [wckit.wc-builder :as wc-builder]
    [clojure.java.io :as io]
    )
  )

(defprotocol WCKitProtocol
  "Interface for core wckit usage."
  (size [this, width, height])
  (font-color [this, c])
  (background-color [this, c])
  (input [this, args])
  (spit-png [this, filepath])
  )
  

(defrecord WCKitProto [
    input-data,
    limit,
    size,
    font-size,
    font-color,
    background-color
  ]
  WCKitProtocol
    (size [this, width, height]
      (assoc this :size [width, height]))
    (font-color [this, c]
      (assoc this :font-color c))
    (background-color [this, c]
      (assoc this :background-color c))
    (input [this, args]
      (wc-input/input this args))
    (spit-png [this, filepath]
      (.writeToFile
        (wc-builder/build this) filepath))
    )

(def defaults
  {
    :input-data {}
    :size [800, 600]
    :limit 100
    :font-size 40
    :font-color "white"
    :background-color "blue"
  })
 

(defn create-new []
  (map->WCKitProto defaults))

