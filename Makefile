problem:
	mkdir src/advent_of_code_20/problem$(number)/
	mkdir test/advent_of_code_20/problem$(number)/
	cp src/advent_of_code_20/solution_template.clj src/advent_of_code_20/problem$(number)/solution.clj
	cp test/advent_of_code_20/solution_test_template.clj test/advent_of_code_20/problem$(number)/solution_test.clj
	gsed -i "s|N|$(number)|g" src/advent_of_code_20/problem$(number)/solution.clj
	gsed -i "s|N|$(number)|g" test/advent_of_code_20/problem$(number)/solution_test.clj
