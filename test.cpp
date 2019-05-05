#include <bits/stdc++.h>

using namespace std;

#define maxn 802
#define M 1061109567
typedef long long ll;
typedef pair<int, int> pii;

int l, s, m, g;
vector<char> ch = {'A', 'C', 'G', 'T'};
vector<string> valid;
vector<int> graph[1000002];

int check(string x) {
    int index = lower_bound(valid.begin(), valid.end(), x) - valid.begin();
    if (valid[index] == x) {
        return index;
    }
    return -1;
}

int bfs(string beg, string end){
    int st = lower_bound(valid.begin(), valid.end(), beg) - valid.begin();
    int ed = lower_bound(valid.begin(), valid.end(), end) - valid.begin();

    queue<int> q;
    q.push(st);
    vector<bool> vis(s);
    vector<int> dis(s);
    while(!q.empty()){
        int u = q.front(); q.pop();
        for(int v: graph[u]){
            if(!vis[v]){
                if(v == ed){
                    return dis[u] + 1;
                }
                vis[v] = true;
                dis[v] = dis[u]+1;
                q.push(v);
            }
        }
    }
    return INT_MAX;
}

int main() {
    freopen("DATA_20.TXT", "r", stdin);
    cin >> l >> s;
    for (int i = 0; i < s; i++) {
        string x;
        cin >> x;
        valid.push_back(x);
    }
    sort(valid.begin(), valid.end());

    for (int i = 0; i < s; i++) {
        for (int j = 0; j < l; j++) {
            for (char c: ch) {
                if (valid[i][j] != c) {
                    string str1 = valid[i];
                    str1[j] = c;
                    int checked = check(str1);
                    if (checked != -1) {
                        graph[i].push_back(checked);
                    }
                }
            }
        }
        for (int j = 1; j < l; j++) {
            string str1 = valid[i];
            swap(str1[j], str1[j - 1]);
            int checked = check(str1);
            if (checked != -1) {
                graph[i].push_back(checked);
            }
        }
    }

    cin >> m >> g;

    for(int t = 0; t < g; t++){
        string beg, end;
        cin >> beg >> end;

        int mut = bfs(beg, end);

        if(mut > m){
            if(mut == INT_MAX) mut = -1;
            printf("NO\n");
            printf("%d\n", mut);
        }else{
            printf("YES\n%d\n", mut);
        }
    }
}
