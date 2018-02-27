#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

char move(int w, char map[][w], char direction, int breaker, int *x, int *y, int inverted);
int validMove(int w, char map[][w], int *x, int *y, char direction, int breaker);
void printMap(int h, int w, char map[][w], int x, int y);

int main()
{
    int L;
    int C;
    scanf("%d%d", &L, &C); fgetc(stdin);
    char map[L][C];
    int t1x,t1y,t2x,t2y,x,y,endX,endY;
    t1x = -1;
    int MAX = 10000;
    int count = 0;
    int breaker = 0;
    int inverted = 0;
    char *route[MAX];

    for (int i = 0; i < L; i++) {
        char row[102];
        fgets(row, 102, stdin);
        for (int j = 0; j < C; j++) {
            map[i][j] = row[j];
            if (row[j] == 'T') {
                if (t1x == -1) {
                    t1x = j;
                    t1y = i;
                }
                else {
                    t2x = j;
                    t2y = i;
                }
            }
            else if (row[j] == '@') {
                x = j;
                y = i;
            }
            else if (row[j] == '$') {
                endX = j;
                endY = i;
            }
        }
    }

    char direction = 'S';
    
    while (x != endX || y != endY) {
        //printMap(L,C,map,x,y);
        if (count > MAX-1) {
            printf("LOOP\n");
            return 0;
        }
        direction = move(C,map,direction,breaker,&x,&y,inverted);
        if (direction == 'S') {
            route[count++] = "SOUTH";
        }
        else if (direction == 'N') {
            route[count++] = "NORTH";
        }
        else if (direction == 'E') {
            route[count++] = "EAST";
        }
        else {
            route[count++] = "WEST";
        }
        if (map[y][x] == '$') {
            for (int i = 0; i < count; i++) {
                printf("%s\n",route[i]);
            }
            return 0;
        }
        else if (map[y][x] == 'S' || map[y][x] == 'W' || map[y][x] == 'E' || map[y][x] == 'N') {
            direction = map[y][x];
        }
        else if (map[y][x] == 'T') {
            if (x == t1x && y == t1y) {
                x = t2x;
                y = t2y;
            }
            else {
                x = t1x;
                y = t1y;
            }
        }
        else if (map[y][x] == 'B') {
            breaker = (breaker+1)%2;
        }
        else if (map[y][x] == 'I') {
            inverted = (inverted+1)%2;
        }
        else if (map[y][x] == 'X' && breaker == 1) {
            map[y][x] = ' ';
        }
    }
    
    

    //printMap(L,C,map,x,y);

    return 0;
}

char move(int w, char map[][w], char direction, int breaker, int *x, int *y, int inverted) {
    char moves[4] = { 'S','E','N','W' };
    if (inverted == 1) {
        char temp[] = { 'W','N','E','S' };
        for (int i = 0; i < 4; i++) {
            moves[i] = temp[i];
        }
    }
    if (validMove(w,map,x,y,direction,breaker)) { // if the current direction is valid
        return direction;
    }
    else { // cycle through the direction array for the first valid move
        fprintf(stderr,"%c invalid\n",direction);
        for (int i = 0; i < 4; i++) {
            fprintf(stderr,"trying %c\n",moves[i]);
            if (moves[i] == direction) {
                continue;
            }
            if (validMove(w,map,x,y,moves[i],breaker)) {
                return moves[i];
            }
            fprintf(stderr,"%c invalid\n",moves[i]);
        }
    }
}

int validMove(int w, char map[][w], int *x, int *y, char direction, int breaker) {
    int valid = 1;
    switch (direction) {
        case 'S':
            if (map[*y+1][*x] == '#' || (map[*y+1][*x] == 'X' && breaker == 0)) {
                valid = 0;
                fprintf(stderr,"map[%d][%d] = %c\n",*y+1,*x,map[*y+1][*x]);
            }
            else {
                (*y)++;
            }
            break;
        case 'N':
            if (map[*y-1][*x] == '#' || (map[*y-1][*x] == 'X' && breaker == 0)) {
                valid = 0;
                fprintf(stderr,"map[%d][%d] = %c\n",*y-1,*x,map[*y-1][*x]);
            }
            else {
                (*y)--;
            }
            break;
        case 'E':
            if (map[*y][*x+1] == '#' || (map[*y][*x+1] == 'X' && breaker == 0)) {
                valid = 0;
                fprintf(stderr,"map[%d][%d] = %c\n",*y,*x+1,map[*y][*x+1]);
            }
            else {
                (*x)++;
            }
            break;
        case 'W':
            if (map[*y][*x-1] == '#' || (map[*y][*x-1] == 'X' && breaker == 0)) {
                valid = 0;
                fprintf(stderr,"map[%d][%d] = %c\n",*y,*x-1,map[*y][*x-1]);
            }
            else {
                (*x)--;
            }
            break;
    }
    return valid;
}

void printMap(int h, int w, char map[][w], int x, int y) {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            if (i == y && j == x) {
                fprintf(stderr,"P");
                continue;
            }
            fprintf(stderr,"%c",map[i][j]);
        }
        fprintf(stderr,"\n");
    }
    return;
}