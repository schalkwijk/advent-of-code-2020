(ns advent-of-code-20.problem7.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn to-int [string]
  (Integer/parseInt string))

(defn- create-bag [bag-definition]
  (let [[bag-count color] (str/split bag-definition #" " 2)]
    {:count (to-int bag-count) :color color}))

(defn baggify [raw-string]
  (let [split (map str/trim (str/split (str/replace raw-string #"[.,]|contain" "") #"bags?"))]
    [(first split) (map create-bag (filter #(not= "no other" %) (rest split)))]))

(defn- add-to-graph [graph [parent-bag child-bags]]
  (reduce #(update %1 %2 (fn [parents] (cons parent-bag parents))) graph (map :color child-bags)))

(defn graphify [bags]
  (reduce add-to-graph {} (map baggify bags)))

(defn- add-to-reverse-graph [graph [parent-bag child-bags]]
  (assoc graph parent-bag child-bags))

(defn reverse-graphify [bags]
  (reduce add-to-reverse-graph {} (map baggify bags)))

(defn- traverse [graph nodes]
  (let [parents (set (mapcat graph nodes))
        new-nodes (set/union nodes parents)]
    (if (= (count nodes) (count new-nodes))
      nodes
      (traverse graph new-nodes))))

(defn- reverse-traverse [])

(defn- count-children [graph {:keys [color count]}]
  (* count (reverse-traverse graph color)))

(defn- reverse-traverse [graph bag]
  (let [children (graph bag)]
    (inc (apply + (map #(count-children graph %) children)))))

(defn part1 [bags]
  (let [graph (graphify bags)]
    (dec (count (traverse graph (set ["shiny gold"]))))))

(defn part2 [bags]
  (let [graph (reverse-graphify bags)]
    (dec (reverse-traverse graph "shiny gold"))))
