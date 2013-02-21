"""
    System for checking number of comments in a file
"""
import os
import re

totalFiles = 0
#tabAmount = ""
totalLines = 0
totalComments = 0
totalWords = 0;
def checkJava(fileContents):
    global totalLines
    global totalComments
    global totalFiles
    global totalWords
    totalFiles += 1
    
    comment = False
    numOfComments = 0
    numOfWords = 0
    print(str(len(fileContents)) + " Lines of code")
    for line in fileContents:
        #print(line)
        #numOfWords = numOfWords + len(re.findall(r'[A-Za-z0-9\-*+]+', line))
        numOfWords = numOfWords + len(re.findall(r'[A-Za-z0-9\.]+', line))
        #numOfWords = numOfWords + len(re.findall(r'\S+', line))
        if comment:
            numOfComments += 1
            if(line.find("*/") != -1):
                comment = False
        elif line.find("//")!= -1: 
            numOfComments += 1
        elif line.find("/*") != -1:
            comment = True
            numOfComments += 1
        
    print(str(numOfComments) + " Comments")
    print(str((numOfComments / len(fileContents)) * 100) + " Percentage")
    print(str(numOfWords) + " Words")
          
    totalLines = totalLines + len(fileContents)
    totalComments = totalComments +  numOfComments
    totalWords += numOfWords

def loadFile(fileName):
    fileContents = []
    f = open(fileName, 'r')
    for line in f:
        fileContents.append(line)
    f.close()
    #print(fileContents)
    return fileContents


def loadAllFiles():
    fileList = os.listdir(os.curdir)   
    
    for file in fileList:
        try:
            if(file != "commentChecker.py"):
                print(file)
                checkJava(loadFile(file))
                print("")
                print("")
        except:
            os.chdir(file)
            print("")
            print("")
            print("")
            loadAllFiles()
            os.chdir("../")
def printFinalStats():
    global totalLines, totalFiles, totalComments, totalWords
    
    print("")
    print("")
    print("")
    print("Total Files: " + str(totalFiles)) 
    print("Total lines: " + str(totalLines))
    print("Total Comments: " + str(totalComments))
    print("Total Words: " + str(totalWords))
    
    print("Overall Percentage: " + str((totalComments / totalLines) * 100))

    
    

def printAll():
    global totalLines, totalFiles, totalComments, totalWords

    totalLines = 0
    totalFiles = 0
    totalComments = 0
    totalWords = 0
    
    loadAllFiles()
    printFinalStats()
    


