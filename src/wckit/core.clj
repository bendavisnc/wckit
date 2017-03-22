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
  (limit [this, n])
  (font-color [this, c])
  (font-size [this, v])
  (max-font-size [this, v])
  (min-font-size [this, v])
  (background-color [this, c])
  (input [this, args])
  (spit-png [this, filepath])
  )
  

(defrecord WCKitProto [
    input-data,
    limit,
    size,
    font-data,
    background-color
  ]
  WCKitProtocol
    (size [this, width, height]
      (assoc this :size [width, height]))
    (limit [this, v]
      (assoc this :limit v))
    (font-color [this, c]
      (assoc-in this [:font-data :color] c))
    (font-size [this, v] ; todo - cleanup
      (assoc-in  
        (assoc-in this [:font-data :max-size] v)
          [:font-data :min-size] v))
    (max-font-size [this, v]
      (assoc-in this [:font-data :max-size] v))
    (min-font-size [this, v]
      (assoc-in this [:font-data :min-size] v))
    (background-color [this, c]
      (assoc this :background-color c))
    (input [this, args]
      (wc-input/input this args))
    (spit-png [this, filepath]
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
      }
    :background-color "blue"
  })
 

(defn create-new []
  (map->WCKitProto defaults))

