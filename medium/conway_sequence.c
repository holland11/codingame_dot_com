#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
 
 
void addToString(char *string, int n1, int n2, int *index);
char * nextLine(char *line);
char * intToString(int number);
void printCurrent(char *string, int length);
 
int main()
{
    int R;
    scanf("%d", &R);
    int L;
    scanf("%d", &L);

    // Write an action using printf(). DON'T FORGET THE TRAILING \n
    // To debug: fprintf(stderr, "Debug messages...\n");
    
    char *line = intToString(R);
    fprintf(stderr,"%s|%d|%d\n",line,R,L);
    
    for (int i = 1; i < L; i++) {
        char *temp = nextLine(line);
        free(line);
        line = temp;
        //fprintf(stderr,"%d:%s\n",i,line);
    }

    printf("%s\n",line);

    return 0;
}

char * nextLine(char *line) {
    int length = strlen(line);
    char *result = malloc(length*2);
    int count = 0;
    int i = 0;
    int start = 0;
    int currentNumber = -1;
    int numberCount = 0;
    
    fprintf(stderr,"start string:%s\n",line);
    
    while (line[i] != '\0') {
        if (line[i] == ' ') {
            line[i] = '\0';
            int temp = atoi(&line[start]);
            //fprintf(stderr,"temp%d:%d|start:%d|line:%s\n",i,temp,start,line);
            if (start == 0) {
                currentNumber = temp;
                numberCount++;
                start = i+1;
            }
            else {
                if (temp == currentNumber) {
                    numberCount++;
                    start = i+1;
                }
                else {
                    addToString(result,numberCount,currentNumber,&count);
                    //count += log10(currentNumber)+1;
                    //count += log10(numberCount)+1;
                    //count += 2;
                    currentNumber = temp;
                    numberCount = 1;
                    start = i+1;
                }
            }
        }
        i++;
    }
    fprintf(stderr,"post-loop string:");
    printCurrent(result,count);
    if (start == 0) {
        numberCount = 1;
        currentNumber = atoi(&line[start]);
        fprintf(stderr,"count:%d,num:%d\n",numberCount,currentNumber);
    }
    else {
        int temp = atoi(&line[start]);
        if (temp == currentNumber) {
            numberCount++;
        }
        else {
            addToString(result,numberCount,currentNumber,&count);
            numberCount = 1;
            currentNumber = temp;
        }
    }
    addToString(result,numberCount,currentNumber,&count);
    //fprintf(stderr,"end string: %s\n",result);
    //count += log10(currentNumber)+log10(numberCount)+3;
    result[count-1] = '\0';
    fprintf(stderr,"end string:%s\n",result);
    return result;
}

void printCurrent(char *string, int length) {
    fprintf(stderr,"len:%d\n",length);
    for (int i = 0; i < length; i++) {
        fprintf(stderr,"%c",string[i]);
    }
    fprintf(stderr,"\n");
}

void addToString(char *string, int n1, int n2, int *index) {
    char *s1 = intToString(n1);
    char *s2 = intToString(n2);
    //fprintf(stderr,"%s %s\n",s1,s2);
    //fprintf(stderr,"index:%d\n",*index);
    
    for (int i = 0; i < strlen(s1); i++) {
        string[(*index)++] = s1[i];
    }
    string[(*index)++] = ' ';
    //fprintf(stderr,"string:%s,index:%d\n",string,*index);
    for (int i = 0; i < strlen(s2); i++) {
        string[(*index)++] = s2[i];
    }
    string[(*index)++] = ' ';
    free(s1);
    free(s2);
}

char * intToString(int number) {
    //fprintf(stderr,"%d\n",number);
    if (number == 0) {
        char *result = "0";
        return result;
    }
    int startingPower = log10(number);
    char *result = malloc(sizeof(char)*(startingPower+1)+1);
    int count = 0;
    for (int i = startingPower; i >= 0; i--) {
        int temp = number / (int)pow(10,i);
        number -= temp * pow(10,i);
        result[count++] = '0' + temp;
        //fprintf(stderr,"%c %d %d\n",result[count-1],temp,number);
    }
    result[count] = '\0';
    return result;
}