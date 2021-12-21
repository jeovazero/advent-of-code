import Data.List (partition)
import Debug.Trace

initialState input = replicate (length (head input)) (0,0)

part1' acc cur = 
    fmap
        (\((zer,one),c) ->
            if c == '0'
            then (zer + 1,one)
            else (zer,one+1)
        )
    $ zip acc cur

value = foldl (\acc cur -> acc * 2 + cur) 0

part1 input = let
    ans = foldl part1' (initialState input) input
    gamma = fmap (\(a,b) -> if a < b then 1 else 0) ans
    epsilon = fmap (\x -> mod (x + 1) 2) gamma
  in
    value gamma * value epsilon

part2' acc [] _ = value $ reverse acc
part2' acc ("":_) _ = value $ reverse acc
part2' acc (str:[]) _ = value $ reverse acc ++ fmap (read . pure) str
part2' acc input mcond = let
    (zero,one) = partition (\(x:_) -> x == '0') input
    (nextInput, digit) = if mcond $ length zero > length one then (zero,0) else (one,1)
    input' = fmap tail nextInput
  in
    part2' (digit:acc) input' mcond
  
part2 input = let
    gamma = part2' [] input id
    epsilon = part2' [] input not
  in
    gamma * epsilon

solve input = show $ [part1 input, part2 input]

main = interact $ solve . lines
