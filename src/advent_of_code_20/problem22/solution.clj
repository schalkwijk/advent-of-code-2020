(ns advent-of-code-20.problem22.solution
  (:require [clojure.string :as str]))

(defn- deck-is-empty? [player]
  (= (count (:deck player)) 0))

(defn- to-int [string] (Integer/parseInt string))

(defn- create-deck [input]
  (let [split (str/split input #"\n")
        player-name (first split)
        cards (rest split)]
    {:player-name player-name :deck (apply list (map to-int cards))}))

(defn play-card [[{deck-a :deck :as player-a} {deck-b :deck :as player-b}]]
  (let [player-a-card (first deck-a)
        player-b-card (first deck-b)
        player-a (update (update player-a :deck (partial apply list)) :deck pop)
        player-b (update (update player-b :deck (partial apply list)) :deck pop)]
    (if (> player-a-card player-b-card)
      [(update player-a :deck concat (list player-a-card player-b-card)) player-b]
      [player-a (update player-b :deck concat (list player-b-card player-a-card))])))

(defn- score-deck [deck]
  (let [size (count deck)]
    (->> size
         (range 0)
         reverse
         (map inc)
         (map #(vector %1 %2) deck)
         (map (partial apply *))
         (apply +))))

(defn part1 [input]
  (->> (range)
       (reductions (fn [decks _] (play-card decks)) (map create-deck input))
       (filter #(or (deck-is-empty? (first %)) (deck-is-empty? (second %))))
       first
       (filter #(not (deck-is-empty? %)))
       first
       :deck
       score-deck))
