(ns wckit.core
  (:import
    [wckit.java.core IWCKit]
    [wckit.java.builder WCBuilder]
    )

  (:require
    [wckit.iwc-util :as iwc-util]
    [clojure.java.io :as io]
    [common.helpers :refer [init-kumo-logging!]]
    [clojure.edn :as edn]
    )
  (:gen-class)
  )

(defrecord WCKitProto [
    wc-type
    input
    min-word-length
    word-count
    width
    height
    collision
    padding
    backgrounds
    background-color
    color
    polar-blend-mode
    font-scalar
    font-size-min
    font-size-max
    font-weight
    font-type
    encoding
    word-start
    normalizer
    tokenizer
  ]
  IWCKit
    (fromEdn [this, filepath]
      (map->WCKitProto
        (merge
          this
          (edn/read-string
            ;(slurp (io/resource filepath)))
            (slurp filepath))
          {:config-origin filepath})))

    (spitPng [this, filepath]
      (.writeToFile
        (.buildWordCloud
          (new WCBuilder this))
        filepath))
    (spitPng [this]
      (.spitPng this (.getOutputSource this)))
    ; setters
    (wcType [this t] ; standard, polar, or layered
      (assoc this :wc-type t))
    (input [this ss]
      (assoc this :input ss))
    (minWordLength [this n]
      (assoc this :min-word-length n))
    (wordCount [this w]
      (assoc this :word-count w))
    (width [this w]
      (assoc this :width w))
    (height [this h]
      (assoc this :height h))
    (collision [this m]
      (assoc this :collision m))
    (padding [this n]
      (assoc this :padding n))
    (backgrounds [this bgs]
      (assoc this :backgrounds bgs))
    (backgroundColor [this c]
      (assoc this :background-color c))
    (color [this s]
      (assoc this :color s))
    (polarBlendMode [this m]
      (assoc this :polar-blend-mode m))
    (fontScalar [this fs]
      (assoc this :font-scalar fs))
    (fontSizeMin [this fsm]
      (assoc this :font-size-min fsm))
    (fontSizeMax [this fsm]
      (assoc this :font-size-max fsm))
    (fontWeight [this fw]
      (assoc this :font-weight fw))
    (fontType [this ft]
      (assoc this :font-type ft))
    (encoding [this e]
      (assoc this :encoding e))
    (wordStart [this ws]
      (assoc this :word-start ws))
    (normalizer [this norms]
      (assoc this :normalizer norms))
    (tokenizer [this t]
      (assoc this :tokenizer t))
    ; getters
    (getBackgrounds [this]
      (:backgrounds this))
    (getCollisionMode [this]
      (iwc-util/get-collision-mode this))
    (getBackgroundColor [this]
      (iwc-util/get-background-color this))
      ;(:background-color this))
    (getColors [this]
      (iwc-util/get-colors this))
    (getRawColorVal [this]
      (:color this))
    (getLayeredColors [this]
      (iwc-util/get-layered-colors this))
    (getPolarBlendMode [this]
      (iwc-util/get-polar-blend-mode this))
    (getFontScalarType [this]
      (iwc-util/get-font-scalar-type this))
    (getFontSizeMin [this]
      (:font-size-min this))
    (getFontSizeMax [this]
      (:font-size-max this))
    (getFontType [this]
      (:font-type this))
    (getFontWeight [this]
      (iwc-util/get-font-weight this))
    (getCharacterEncoding [this]
      (:encoding this))
    (getHeight [this]
      (:height this))
    (getInputSources [this]
      (:input this))
    (getMinWordLength [this]
      (:min-word-length this))
    (getNormalizers [this]
      (iwc-util/get-normalizers this))
    (getOutputSource [this]
      (iwc-util/get-output-source this))
    (getPadding [this]
      (:padding this))
    (getTokenizer [this]
      (iwc-util/get-tokenizer this))
    (getType [this]
      (iwc-util/get-type this))
    (getWidth [this]
      (:width this))
    (getWordCount [this]
      (:word-count this))
    (getWordStartType [this]
      (iwc-util/get-word-start-type this))
      )



(def ^:private defaults
  {
    :wc-type "standard"
    :word-count 100
    :input ["./resources/starwars/starwars.txt"]
    :min-word-length 0
    :width 640
    :height 480
    :collision "pixel-perfect"
    :padding 2
    :backgrounds []
    :background-color "black"
    ;:color "white"
    :color "0xff0000"
    :polar-blend-mode "even"
    :font-scalar "linear"
    :font-size-min 10
    :font-size-max 50
    :font-weight "bold"
    :font-type "impact"
    :encoding "UTF-8"
    :word-start "center"
    :normalizer []
    :tokenizer "whitespace"
  })


;;
;;
;; Exposed helper methods...

(defn create-new []
  (map->WCKitProto defaults))

(defn from-edn [wck filepath]
  (.fromEdn wck filepath))

(defn spit-png
  ([wck filepath]
    (.spitPng wck filepath))
  ([wck]
   (.spitPng wck)))

(defn createNew []
  (create-new))


(init-kumo-logging!)

(defn -main [config-path]
  (->
    (create-new)
    (from-edn config-path)
    (spit-png)))



