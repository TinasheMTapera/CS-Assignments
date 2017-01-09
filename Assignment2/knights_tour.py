#!/usr/bin/env python

'''
Title: Knight's Tour Program Assignment

Couse: CS265, Fall 2016

Written by: Tinashe M. Tapera
'''
'''
Prompt:
You will write a Python program that attempts to solve the Knight's Tour for
a given board by luck. The Knight's Tour is an attempt to move the knight on
a board, such that each space is visited exactly once. See the Web for more
information.

Your program will read 3 arguments: rows, columns, attempts . The first 2
arguments are the size of the board, rows x columns. The last argument is
the number of attempts to make.

The knight will start at (1, 1). Look around, see what moves are available,
and randomly choose one (note, that is not the same as randomly choosing a
move, and then seeing if it's legal). If you have no more moves (and you
haven't covered the board), then the knight didn't make it.

You then continue with a fresh attempt, starting at (1, 1), until the knight
succeeds, or you've used up the number of attempts. At the end, whichever
the outcome, you will print a history of the knight's last attempt, w/a
"success" or "failure" message. E.g., given a 3x3 board, output:

 FAIL:
 1  6  3 
 4  x  8 
 7  2  5
'''

'''
Plan:
a number of objectives:
1:DONE read in and display the instructions "row", "colums", "attempts"
2:DONE define the board based on (1)
3:DONE represent the knight on the board
4: define a knight's move
5: move a knight and see output (from x1y1 to x2y2)
6: make an attempt by randomising moves

'''
#get the argv and assign
import sys
import random

if len(sys.argv)<3:
	print 'Not a square board! Please input an NxN board & no. of attempts.\nExiting...'
	sys.exit()

if sys.argv[1] != sys.argv[2]:
	print 'Not a square board! Please input an NxN board & no. of attempts.\nExiting...'
	sys.exit()
else:	
	rows, cols = sys.argv[1], sys.argv[2]

if len(sys.argv)<4:
	print 'No number of attempts given. Defaulting to 1000'
	attempts = 1000
else:
	attempts = int(sys.argv[3])

print 'rows =',rows,'cols =',cols,'attempts =',attempts, '\n'

#define a board:

	#first use coordinates to make a list of x,y pairs
coordinates=[]
for x in range(int(rows)):
	for y in range(int(cols)):
		coordinates.append((x,y))

	#then map x.y pairs to a value in a dictionary
grid = {}
for i in range(len(coordinates)):
	grid[coordinates[i]] = 0

#represent knight on board
knight=[0,0]
grid[tuple(knight)]= 1

#define possible moves
allMoves = {
	'upRight': (1,2),
	'rightUp': (2,1),
	'rightDown': (2,-1),
	'downRight': (1,-2),
	'downLeft': (-1,-2),
	'leftDown': (-2,-1),
	'leftUp': (-2,1),
	'upLeft': (-1,2)
}
#moves = 1

#move is defined as 2 steps, one step
def move():
	legalMoves = []

	#list all the possible legal moves from this position
	for i in allMoves:
		temp =  (knight[0] + allMoves[i][0],
knight[1]+allMoves[i][1])
		#print temp, 'possible?'
		if temp in coordinates and grid[temp] < 1:
			#print 'YES!'
			legalMoves.append(temp)
		else:
			dummy = 'aaaayyyeeeee, dono why it needs this line!!'
	
	#print "all available moves:", legalMoves
	#if there are no moves in this list, there are no more moves left
	#and we've used  1 attempt
	
	if not legalMoves:
		#print 'no legal moves left'	
		return False;

	#pick a random move from these legal moves,
	#increment moves, assign knight to new position, 
	#assign the moves to the new position
	nextMove = random.choice(legalMoves)
	#print 'try move', nextMove
	#print 'moving from', knight
	knight[0] = nextMove[0]
	knight[1] = nextMove[1]
	#print 'to', knight
	grid[tuple(knight)] = moves
	
	return True;

#define the grid output
def output_grid(coordinateSys, gridSys):
	#for i in range(len(coordinateSys)):
	#	print gridSys[coordinateSys[i]]
		
	for x in range(int(rows)):
		for y in range(int(cols)):
			print gridSys[y,x],
		print ' '

#play the game

win = all(x!=0 for x in grid.values())
originalAttempts = attempts
while attempts > 0 and not win:

	#reset knight and board
	grid = {}
	for i in range(len(coordinates)):
		grid[coordinates[i]] = 0
	knight=[0,0]
	moves=1
	grid[tuple(knight)]= 1

	#move!
	moving = True
	while moving:
		moving = move()
		moves += 1
		win = all(x!=0 for x in grid.values())
	if win:
		print 'found it! attempt number: {}'.format(originalAttempts-attempts)
		finalGrid = grid
		break

	attempts -= 1
	win = all(x!=0 for x in grid.values())
	finalGrid = grid

if not win:
	print 'failed to complete the knights tour'
output_grid(coordinates, finalGrid)
