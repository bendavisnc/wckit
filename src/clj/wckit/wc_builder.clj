(ns wckit.wc-builder
  (:require
    [common.colors :as colors]
    [wckit.helpers :refer [build-color-pallette, build-color-pallette]]
    )
  (:import
    (com.kennycason.kumo.nlp FrequencyAnalyzer)
    (com.kennycason.kumo WordCloud)
    (com.kennycason.kumo CollisionMode)
    (com.kennycason.kumo.font.scale LinearFontScalar)
    (com.kennycason.kumo.font KumoFont FontWeight)
    )
  )


(defn build [wck]
  "Given a word cloud kit, build up the corresponding kumo.WordCloud object."
  (let [
      dimension (apply #(new java.awt.Dimension %1 %2) (:size wck))
      frequency-analyzer 
        (doto
          (new FrequencyAnalyzer)
          (.setWordFrequenciesToReturn (:limit wck)))
      word-frequencies (.load frequency-analyzer (get-in wck [:input-data :input-source]))
      kumo-wc (new WordCloud dimension, CollisionMode/RECTANGLE)
      min-font-size (get-in wck [:font-data, :min-size])
      max-font-size (get-in wck [:font-data, :max-size])
      font-style
        (new KumoFont
          ^String (get-in wck [:font-data, :style])
           FontWeight/BOLD)
      font-colors (get-in wck [:font-data, :color])
    ]
    (doto
      kumo-wc
      (.setBackgroundColor 
        (colors/create-color (:background-color wck)))
      (.setPadding 2)
      (.setFontScalar (new LinearFontScalar min-font-size max-font-size))
      (.setKumoFont font-style)
      (.setColorPalette
        (apply build-color-pallette
          (if
            (seq? font-colors)
            font-colors
            [font-colors])))
      (.build word-frequencies))))





