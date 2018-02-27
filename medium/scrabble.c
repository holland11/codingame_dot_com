#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
 
int charToScore(char c);
int wordToScore(char *word);

 
int main()
{
    int N;
    scanf("%d", &N); fgetc(stdin);
    
    char *words[N];
    
    for (int i = 0; i < N; i++) {
        char W[31];
        fgets(W, 31, stdin);
        //fprintf(stderr,"%s%d\n",W,strlen(W));
        int l = strlen(W)-1;
        words[i] = malloc(sizeof(char)*(l+1));
        for (int j = 0; j < l; j++) {
            words[i][j] = W[j];
        }
        words[i][l] = '\0';
        //fprintf(stderr,"%s\n",words[i]);
    }
    
    char LETTERS[9];
    fgets(LETTERS, 9, stdin);
    //fprintf(stderr,"%s%d\n",LETTERS,strlen(LETTERS));
    int l1 = strlen(LETTERS) - 1;

    char copy[l1+1];
    int score;
    int max = 0;
    char *result;
    //fprintf(stderr,"%s\n",copy);
    
    for (int i = 0; i < N; i++) {
        strcpy(copy,LETTERS);
        int l2 = strlen(words[i])+1;
        //fprintf(stderr,"%s %d | %s %d\n",copy,strlen(copy),words[i],strlen(words[i]));
        fprintf(stderr,"%s",copy);
        fprintf(stderr,"%s\n",words[i]);
        score = 0;
        for (int j = 0; j < l2; j++) {
            if (words[i][j] == '\0') {
                score = (wordToScore(words[i]));
                if (score > max) {
                    result = words[i];
                    max = score;
                }
                break;
            }
            int found = 0;
            for (int k = 0; k < l1; k++) {
                if (words[i][j] == copy[k]) {
                    copy[k] = '\0';
                    found = 1;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        fprintf(stderr,"%d\n",score);
    }

    printf("%s\n",result);

    return 0;
}

int wordToScore(char *word) {
    int length = strlen(word);
    int result = 0;
    for (int i = 0; i < length; i++) {
        result += charToScore(word[i]);
    }
    return result;
}

int charToScore(char c) {
    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'n' || c == 'r' || c == 't' || c == 'l' || c == 's' || c == 'u') {
        return 1;
    }
    if (c == 'd' || c == 'g') {
        return 2;
    }
    if (c =='b' || c == 'c' || c == 'm' || c == 'p') {
        return 3;
    }
    if (c == 'f' || c == 'h' || c == 'v' || c == 'w' || c == 'y') {
        return 4;
    }
    if (c == 'k') {
        return 5;
    }
    if (c == 'j' || c == 'x') {
        return 8;
    }
    if (c =='q' || c == 'z') {
        return 10;
    }
    return -5000;
}