(ns advent-of-code-20.problem4.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem4.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "first sample input"
    (let [input (map #(str/replace % "\n" " ") (str/split (slurp "test/advent_of_code_20/problem4/input.txt") #"\n\n"))]
      (is (= 2 (part1 input))))))

(deftest part2-test
  (testing "invalid inputs"
    (let [input ["eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926"]]
      (is (= 0 (part2 input))))
    (let [input ["iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946"]]
      (is (= 0 (part2 input))))
    (let [input ["hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277"]]
      (is (= 0 (part2 input))))
    (let [input ["hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007"]]
      (is (= 0 (part2 input)))))
  (testing "valid inputs"
    (let [input ["pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"]]
      (is (= 1 (part2 input))))
    (let [input ["eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm"]]
      (is (= 1 (part2 input))))
    (let [input ["hcl:#888785 hgt:164cm byr:2001 iyr:2015 cid:88 pid:545766238 ecl:hzl eyr:2022"]]
      (is (= 1 (part2 input))))
    (let [input ["iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"]]
      (is (= 1 (part2 input))))))
