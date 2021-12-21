
part1' (h,d) row =
  case row of
    ("forward", n) -> (h + n, d)
    ("down", n)    -> (h, d + n)
    ("up", n)      -> (h, d - n)

part2' (h,d,a) row =
  case row of
    ("forward", n) -> (h + n, d + a * n, a)
    ("down", n)    -> (h, d, a + n)
    ("up", n)      -> (h, d, a - n)

multPair (a,b) = a * b
multTuple (a,b,c) = a * b

part1 = multPair . foldl part1' (0,0)
part2 = multTuple . foldl part2' (0,0,0)

solve input = show [part1 input, part2 input]

readRow :: [String] -> (String, Int)
readRow (dir:num:_) = (dir,read num)

main = interact $ solve . fmap (readRow . words) . lines
