(ns advent-of-code-20.problem8.solution
  (:require [clojure.string :as str]))

(defn- parse-command [raw-command]
  (let [chunks (str/split raw-command #" ")]
    {:op (first chunks) :val (Integer/parseInt (last chunks))}))

(defn- succesful-termination [{:keys [pc commands]}]
  (>= pc (count commands)))

(defn- terminate? [state]
  (or (succesful-termination state) (contains? (:visited state) (:pc state))))

(defn- assign-visited [state]
  (update state :visited conj (:pc state)))

(defn- run-next-command [{:keys [commands pc] :as state}]
  (let [current-command (nth commands pc)
        state (update state :visited conj (:pc state))]
    (case (:op current-command)
      "nop" (update state :pc inc)
      "acc" (update (update state :pc inc) :acc + (:val current-command))
      "jmp" (update state :pc + (:val current-command)))))

(defn- run-input [state]
  (first (filter terminate? (reductions (fn [state _] (run-next-command state)) state (range)))))

(defn- run-inputs [commands]
  (run-input {:commands commands :acc 0 :pc 0 :visited (set [])}))

(defn part1 [commands]
  (:acc (run-inputs (map parse-command commands))))

(defn mutate [commands pc]
  (let [{:keys [op] :as command} (nth commands pc)]
    (case op
      "nop" (assoc-in commands [pc :op] "jmp")
      "jmp" (assoc-in commands [pc :op] "nop")
      commands)))

(defn- mutations [commands]
  (filter #(not= commands %) (map (partial mutate commands) (range 0 (count commands)))))

(defn part2 [commands]
  (:acc (first (filter succesful-termination (map run-inputs (mutations (vec (map parse-command commands))))))))
