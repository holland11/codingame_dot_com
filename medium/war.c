#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
 
int stringToInt(char *c);
typedef struct Node Node;
typedef struct List List;
int push(List *list, int val);
void clear(List *list);
int pop(List *list);
void printList(List *list);
int war(int n1[], int n2[], int numStored, List *one, List *two);
 
struct Node {
    Node *next;
    int value;
};

struct List {
    Node *root;
    Node *tail;
    int size;
};
 
int main()
{
    int n; // the number of cards for player 1
    List deckOne = { .root = NULL, .size = 0 };
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        char cardp1[4]; // the n cards of player 1
        scanf("%s", cardp1);
        int val = stringToInt(cardp1);
        push(&deckOne,val);
    }
    
    int m; // the number of cards for player 2
    List deckTwo = { .root = NULL, .size = 0 };
    scanf("%d", &m);
    for (int i = 0; i < m; i++) {
        char cardp2[4]; // the m cards of player 2
        scanf("%s", cardp2);
        int val = stringToInt(cardp2);
        push(&deckTwo,val);
    }

    // Write an action using printf(). DON'T FORGET THE TRAILING \n
    // To debug: fprintf(stderr, "Debug messages...\n");
    
    int gameOn = 1;
    int winner = 0;
    int rounds = 0;
    
    while (gameOn) {
        rounds++;
        //fprintf(stderr,"%d\n",deckTwo.size);
        int card1 = pop(&deckOne);
        int card2 = pop(&deckTwo);
        
        if (card1 > card2) {
            push(&deckOne,card1);
            push(&deckOne,card2);
        }
        else if (card2 > card1) {
            push(&deckTwo,card1);
            push(&deckTwo,card2);
        }
        else {
            int n1[deckOne.size];
            n1[0] = card1;
            int n2[deckTwo.size];
            n2[0] = card2;
            
            int valid = war(n1,n2,1,&deckOne,&deckTwo);
            if (valid == -1) {
                printf("PAT\n");
                return 0;
            }
        }
        
        if (deckOne.size == 0) {
            winner = 2;
            gameOn = 0;
        }
        else if (deckTwo.size == 0) {
            winner = 1;
            gameOn = 0;
        }
    }


    printf("%d %d\n",winner,rounds);

    return 0;
}

int war(int n1[], int n2[], int numStored, List *one, List *two) {
    if (one->size < 4 || two->size < 4) {
        return -1;
    }
    for (int i = 0; i < 3; i++) {
        n1[numStored] = pop(one);
        n2[numStored++] = pop(two);
    }
    if (one->root->value == two->root->value){
        n1[numStored] = pop(one);
        n2[numStored++] = pop(two);
        return war(n1,n2,numStored,one,two);
    }
    if (one->root->value > two->root->value) {
        n1[numStored] = pop(one);
        n2[numStored++] = pop(two);
        for (int i = 0; i < numStored; i++) {
            push(one,n1[i]);
        }
        for (int i = 0; i < numStored; i++) {
            push(one,n2[i]);
        }
    }
    else {
        n1[numStored] = pop(one);
        n2[numStored++] = pop(two);
        for (int i = 0; i < numStored; i++) {
            push(two,n1[i]);
        }
        for (int i = 0; i < numStored; i++) {
            push(two,n2[i]);
        }
    }
}

void printList(List *list) {
    Node *node = list->root;
    while (node != NULL) {
        fprintf(stderr,"%d\n",node->value);
        node = node->next;
    }
    return;
}

void clear(List *list) {
    list->root = NULL;
    list->size = 0;
}

int push(List *list, int val) {
    //Node node = { .next = NULL, .value = val };
    Node *node = malloc(sizeof(Node));
    node->value = val;
    
    if (list->root == NULL) {
        list->root = node;
        list->tail = node;
        list->size = 1;
        return 1;
    }
    //fprintf(stderr,"%d ... %d\n",list->root->value,list->tail->value);
    list->tail->next = node;
    list->tail = node;
    list->size++;
    return 1;
}

int pop(List *list) {
    if (list->root == NULL) {
        return -1;
    }
    int result = list->root->value;
    Node *next = list->root->next;
    free(list->root);
    list->root = next;
    list->size--;
    return result;
} 

int stringToInt(char *c) {
    //fprintf(stderr,"%d\n",strlen(c));
    if (c[0] >= '0' && c[0] <= '9') {
        if (strlen(c) == 2) {
            return (int)(c[0] - '0');
        }
        else {
            return 10;
        }
    }
    if (c[0] == 'J') {
        return 11;
    }
    if (c[0] == 'Q') {
        return 12;
    }
    if (c[0] == 'K') {
        return 13;
    }
    if (c[0] == 'A') {
        return 14;
    }
    return -5000;
}