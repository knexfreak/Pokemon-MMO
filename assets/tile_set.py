import sys

def print_file(fname):
	try:
		with open("tileset.dat", "rb") as infile:
			s = ''
			pair = infile.read(4)
			while pair:
				if len(pair) != 4:
					raise Exception
				s += "(%3d, %3d)\t(%3d, %3d)\n" % tuple([ord(c) for c in pair])
				pair = infile.read(4)
			print s
				
	except IOError, e:
		print "No file present."

if __name__ == "__main__":
#	print_file("tileset.dat")
#	
#	inpt = ""
#	while not inpt in ['a', 'w']:
#		inpt = raw_input("Append or rewrite data? [a/w] ")

#	with open("tileset.dat", inpt + "b") as outfile:
#		finished = False
#		while not finished:
#			inpt = ""
#			while not inpt:
#				inpt = raw_input("Enter (space separated) two coordinate pairs (like '1 2 3 4') or nothing to finish:  ")
#				if not inpt:
#					finished = True
#					break
#				inpt = inpt.split(' ')
#				if len(inpt) == 4:
#					inpt = [int(s) for s in inpt]
#				else:
#					inpt = ""
#			if not finished:
#				outfile.write("%c%c%c%c" % tuple(inpt))

#	print_file("tileset.dat")
	if sys.argv[1] == "read":
		print "read"
	elif sys.argv[1] == "write":
		with open(sys.argv[2], "r") as infile:
			lines = infile.readlines()
		with open(sys.argv[3], "w") as outfile:
			for line in lines:
				line = line.split('\t')
				if line[0] == "rect":
					points = line[1].split(',') + line[2].split(',')
					points = [int(s) for s in points]
					outfile.write("\x01%c%c%c%c" % tuple(points))
				elif line[0] == "set":
					print "set"
