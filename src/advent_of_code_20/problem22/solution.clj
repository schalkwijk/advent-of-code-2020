(ns advent-of-code-20.problem22.solution
  (:require [clojure.string :as str]))

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
      [(assoc (update player-a :deck concat (list player-a-card player-b-card)) :winner (empty? (:deck player-b))) player-b]
      [player-a (assoc (update player-b :deck concat (list player-b-card player-a-card)) :winner (empty? (:deck player-a)))])))

(defn- score-deck [deck]
  (let [size (count deck)]
    (->> size
         (range 0)
         reverse
         (map inc)
         (map #(vector %1 %2) deck)
         (map (partial apply *))
         (apply +))))

(defn- play-game [state]
  (->> (range)
       (reductions (fn [decks _] (play-card decks)) state)
       (filter #(or (:winner (first %)) (:winner (second %))))
       first
       (filter :winner)
       first))

(defn part1 [input]
  (->> input
       (map create-deck)
       play-game
       :deck
       score-deck))
