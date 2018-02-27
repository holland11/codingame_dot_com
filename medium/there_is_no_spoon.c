#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Don't let the machines win. You are humanity's last hope...
 **/
int main()
{
    int width; // the number of cells on the X axis
    scanf("%d", &width);
    int height; // the number of cells on the Y axis
    scanf("%d", &height); fgetc(stdin);
    char grid[height][width];
    
    for (int i = 0; i < height; i++) {
        char line[32]; // width characters, each either 0 or .
        fgets(line, 32, stdin); // width characters, each either 0 or .
        fprintf(stderr,"%s",line);
        for (int j = 0; j < width; j++) {
            grid[i][j] = line[j];
        }
    }
    
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (grid[i][j] == '0') {
                printf("%d %d ",j,i);
            }
            else {
                continue;
            }
            if (j+1 < width) {
                for (int k = j+1; k < width; k++) {
                    if (grid[i][k] == '0') {
                        printf("%d %d ",k,i);
                        break;
                    }
                    else if (k+1 == width) {
                        printf("-1 -1 ");
                    }
                }
            }
            else {
                printf("-1 -1 ");
            }
            
            if (i < height-1) {
                for (int k = i+1; k < height; k++) {
                    if (grid[k][j] == '0') {
                        printf("%d %d",j,k);
                        break;
                    }
                    else if (k+1 == height) {
                        printf("-1 -1");
                    }
                }
            }
            else {
                printf("-1 -1");
            }
            printf("\n");
        }
    }
    

    // Write an action using printf(). DON'T FORGET THE TRAILING \n
    // To debug: fprintf(stderr, "Debug messages...\n");


    // Three coordinates: a node, its right neighbor, its bottom neighbor
    //printf("0 0 1 0 0 1\n");

    return 0;
}