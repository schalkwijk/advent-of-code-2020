(ns advent-of-code-20.problem22.solution
  (:require [clojure.string :as str]))

(defn- to-int [string] (Integer/parseInt string))

(defn- create-deck [input]
  (let [split (str/split input #"\n")
        player-name (first split)
        cards (rest split)]
    {:player-name player-name :deck (apply list (map to-int cards))}))

(defn play-card [{:keys [players]}]
  (let [[{deck-a :deck :as player-a} {deck-b :deck :as player-b}] players
        player-a-card (first deck-a)
        player-b-card (first deck-b)
        player-a (update (update player-a :deck (partial apply list)) :deck pop)
        player-b (update (update player-b :deck (partial apply list)) :deck pop)]
    (if (> player-a-card player-b-card)
      {:players [(assoc (update player-a :deck concat (list player-a-card player-b-card)) :winner (empty? (:deck player-b))) player-b]}
      {:players [player-a (assoc (update player-b :deck concat (list player-b-card player-a-card)) :winner (empty? (:deck player-a)))]})))

(defn- score-deck [deck]
  (let [size (count deck)]
    (->> size
         (range 0)
         reverse
         (map inc)
         (map #(vector %1 %2) deck)
         (map (partial apply *))
         (apply +))))

(defn- play-game [play-card-fn state]
  (->> (range)
       (reductions (fn [decks _] (play-card-fn decks)) state)
       (map :players)
       (filter #(or (:winner (first %)) (:winner (second %))))
       first
       (filter :winner)
       first))

(defn part1 [input]
  (let [state (map create-deck input)]
    (score-deck (:deck (play-game play-card {:players state})))))

(defn- play-card-recurse [{:keys [players previous-hands] :as state}]
  (if (contains? (set previous-hands) players)
    (assoc state :players [(assoc (first players) :winner true) (second players)])
    (let [player-a (first players)
          deck-a (:deck player-a)
          player-b (second players)
          deck-b (:deck player-b)
          player-a-card (first deck-a)
          player-b-card (first deck-b)
          player-a (update (update player-a :deck (partial apply list)) :deck pop)
          player-b (update (update player-b :deck (partial apply list)) :deck pop)
          new-deck-a (:deck player-a)
          new-deck-b (:deck player-b)]
      (if (and (>= (count new-deck-a) player-a-card) (>= (count new-deck-b) player-b-card))
        (let [revised-deck-a (take player-a-card new-deck-a)
              revised-deck-b (take player-b-card new-deck-b)
              new-state {:players [(assoc player-a :deck revised-deck-a) (assoc player-b :deck revised-deck-b)] :previous-hands (set [])}
              winner (play-game play-card-recurse new-state)]
          (if (= "Player 1:" (:player-name winner))
            (update (assoc state :players [(assoc (update player-a :deck concat (list player-a-card player-b-card)) :winner (empty? new-deck-b)) player-b]) :previous-hands conj players)
            (update (assoc state :players [player-a (assoc (update player-b :deck concat (list player-b-card player-a-card)) :winner (empty? new-deck-a))]) :previous-hands conj players)))
        (if (> player-a-card player-b-card)
          (update (assoc state :players [(assoc (update player-a :deck concat (list player-a-card player-b-card)) :winner (empty? new-deck-b)) player-b]) :previous-hands conj players)
          (update (assoc state :players [player-a (assoc (update player-b :deck concat (list player-b-card player-a-card)) :winner (empty? new-deck-a))]) :previous-hands conj players)))) ))

(defn part2 [input]
  (let [state (map create-deck input)]
    (score-deck (:deck (play-game play-card-recurse {:players state})))))
