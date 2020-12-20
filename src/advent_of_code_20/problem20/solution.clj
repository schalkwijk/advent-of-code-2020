(ns advent-of-code-20.problem20.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :refer :all]
            [clojure.set :as set]))

(defn- to-int [string]
  (Integer/parseInt string))

(def top 0)
(def right 1)
(def bottom 2)
(def left 3)

(defn- flip [rows]
  (let [[top-row right-row bottom-row left-row] rows]
    [rows
     [bottom-row (reverse right-row) top-row (reverse left-row)]
     [(reverse top-row) left-row (reverse bottom-row) right-row]]))

(defn- create-tile [tile-id index rows]
  {:id (str tile-id "-" index) :raw-id tile-id :rows rows})

(defn- rotate [[top-row right-row bottom-row left-row]]
  [(reverse left-row) top-row (reverse right-row) bottom-row])

(defn create-tiles [raw-tile]
  (let [raw-tile (str/split raw-tile #"\n")
        tile-id (second (re-matches #"Tile ([0-9]+):" (first raw-tile)))
        rows (map #(str/split % #"") (rest raw-tile))
        top-row (first rows)
        right-row (map last rows)
        bottom-row (last rows)
        left-row (map first rows)
        original [top-row right-row bottom-row left-row]]
    (map-indexed (fn [index rows] (create-tile tile-id index rows)) (set (mapcat flip [original (rotate original) (rotate (rotate original)) (rotate (rotate (rotate original)))])))))

(defn- update-matching-tiles [state [tile-a tile-b]]
  (let [bottom-match (= (get (:rows tile-a) bottom) (get (:rows tile-b) top))
        right-match (= (get (:rows tile-a) right) (get (:rows tile-b) left))
        bottom-state (if bottom-match (update-in state [(:id tile-a) bottom] (fnil conj '()) tile-b) state)]
    (if right-match (update-in bottom-state [(:id tile-a) right] (fnil conj '()) tile-b) bottom-state)))

(defn matching-tiles [tiles]
  (reduce update-matching-tiles {} (filter #(apply not= (map :raw-id %)) (cartesian-product tiles tiles))))

(defn- filter-out-tiles [{:keys [board available-tiles] :as state}]
  (let [raw-ids (set (map :raw-id (mapcat vals (vals board))))
        remaining-tiles (filter #(not (contains? raw-ids (:raw-id %))) available-tiles)]
    (assoc state :available-tiles remaining-tiles)))

(defn- generate-next-states [{:keys [available-tiles matching-tiles board] :as state} [row column]]
  (let [top-tile (get-in board [(dec row) column] nil)
        left-tile (get-in board [row (dec column)] nil)
        matching-top-tiles (set (if (nil? top-tile) available-tiles (get-in matching-tiles [(:id top-tile) bottom] [])))
        matching-left-tiles (set (if (nil? left-tile) available-tiles (get-in matching-tiles [(:id left-tile) right] [])))
        valid-tiles (set/intersection matching-left-tiles matching-top-tiles)]
    (map #(filter-out-tiles (assoc-in state [:board row column] %)) valid-tiles)))

(defn part1 [input]
  (let [tiles (mapcat create-tiles input)
        matching-tiles (matching-tiles tiles)
        row-count (int (Math/sqrt (count input)))
        tile-indices (mapcat (fn [index] (map #(vector index %) (range 0 row-count))) (range 0 row-count))
        empty-state {:available-tiles (set tiles) :board {} :matching-tiles matching-tiles}]
    (->> tile-indices
         (reduce (fn [states index] (mapcat #(generate-next-states % index) states)) [empty-state])
         (map :board)
         (map (fn [board] (sort (map :raw-id (map #(get-in board %) [[0 0] [0 (dec row-count)] [(dec row-count) 0] [(dec row-count) (dec row-count)]])))))
         first
         (map to-int)
         (apply *))))

(defn part2 [input]) 10
