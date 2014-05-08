#include <iostream>
#include <map>
using namespace std;

/*
*	Structure Definition
*/
struct Topic
{
	int id;
	float weight;
};

struct TopicLinkList
{
	Topic* value;
	TopicLinkList* pre;
};

struct Weibo
{
	int id;
	float lon;
	float lat;
	TopicLinkList* topics;
	float weight;
};

struct Query
{
	int isQueryTopic;
	int resultCount;
	float lon;
	float lat;
};

/*
*	Heap Sort
*/
int compareWeibo(Weibo* weibo1, Weibo* weibo2)
{
	if(weibo1->weight > weibo2->weight) return 1;
	else if(weibo1->weight < weibo2->weight) return -1;
	else {
		if(weibo1->id > weibo2->id) return 1;
		else if(weibo1->id < weibo2->id) return -1;
		else return 0;
	}
}

void HeapAdjust(Weibo** array, int i, int nLength)
{
	int nChild;
	Weibo* nTemp;
	for(; 2*i+1<nLength; i=nChild)
	{
		nChild=2*i+1;

		if( nChild<nLength-1 && compareWeibo(array[nChild+1], array[nChild])<0 ) ++nChild;

		if( compareWeibo(array[i], array[nChild]) > 0 )
		{
			nTemp = array[i];
			array[i] = array[nChild];
			array[nChild] = nTemp;
		}
		else break;
	}
}

void HeapSort(Weibo** array, int length, int num)
{
	int i;
	Weibo* tmp;

	for(i=length/2-1; i>=0; --i) HeapAdjust(array, i, length);

	int left = length - num - 1;
	if( left < 0 ) left = 0;
	for(i=length-1; i>left; --i)
	{
		tmp = array[i];
		array[i] = array[0];
		array[0]=tmp;

		HeapAdjust(array,0,i);
	}
}

int compareTopic(Topic* t1, Topic* t2)
{
	if(t1->weight > t2->weight) return 1;
	else if(t1->weight < t2->weight) return -1;
	else {
		if(t1->id > t2->id) return 1;
		else if(t1->id < t2->id) return -1;
		else return 0;
	}
}

int partition(Topic** array, int begin, int end)
{
	Topic* x = array[end];// use the last value to be the patition value.
	int middle = begin;// the index of the biggest value of the smaller values.

	for(int j = begin; j<end; j++)
	{
		if(compareTopic(array[j],x) < 0)
		{
			if(j != middle)
			{
				Topic* temp = array[middle];
				array[middle] = array[j];
				array[j]=temp;
			}
			middle++;
		}
	}

	Topic* temp = array[end];
	array[end] = array[middle];
	array[middle] = temp;
	return middle;
}

void QuickSort(Topic** array, int start, int end)
{
	if(start < end)
	{
		int q = partition(array, start, end);
		QuickSort(array, start, q-1);
		QuickSort(array, q+1, end);
	}
}
/*
*	Main
*/
int main()
{
	map<int, Weibo*> mapWeibos;

	int W, T, Q;
	cin>>W>>T>>Q;
	Weibo** allWeibos = new Weibo*[W];
	Topic** allTopics = new Topic*[T];
	Query** allQueries = new Query*[Q];

	// Initialization
	int i, j;
	for(i=0; i<W; i++)
	{
		Weibo* newWeibo = new Weibo();
		cin>>newWeibo->id;
		cin>>newWeibo->lon;
		cin>>newWeibo->lat;
		newWeibo->topics = NULL;
		mapWeibos[newWeibo->id] = newWeibo;
		allWeibos[i] = newWeibo;
	}

	for(i=0; i<T; i++)
	{
		Topic* newTopic = new Topic();
		cin>>newTopic->id;
		allTopics[i] = newTopic;

		int weiboNum = 0;
		cin>>weiboNum;
		for(j=0; j<weiboNum; j++)
		{
			int weiboID = 0;
			cin>>weiboID;

			Weibo* weibo = mapWeibos[weiboID];
			TopicLinkList* list = weibo->topics;
			if( list == NULL ) 
			{
				list = new TopicLinkList();
				list->value = newTopic;
				list->pre = NULL;
				weibo->topics = list;
			} else {
				TopicLinkList* newNode = new TopicLinkList();
				newNode->value = newTopic;
				newNode->pre = list;
				weibo->topics = newNode;
			}
		}
	}

	for(i=0; i<Q; i++)
	{
		Query* newQuery = new Query();
		cin>>newQuery->isQueryTopic;
		cin>>newQuery->resultCount;
		cin>>newQuery->lon;
		cin>>newQuery->lat;
		allQueries[i] = newQuery;
	}

	// Find the result
	for(i=0; i<Q; i++)
	{
		Query* query = allQueries[i];
		for(j=0; j<W; j++)
		{
			Weibo* weibo = allWeibos[j];
			weibo->weight = (query->lon-weibo->lon)*(query->lon-weibo->lon) + (query->lat-weibo->lat)*(query->lat-weibo->lat);
		}

		if( query->isQueryTopic == 0 ) 
		{
			HeapSort(allWeibos, W, query->resultCount);
			for(j=0; j<query->resultCount; j++)
			{
				if(j==0) cout<<allWeibos[W-1-j]->id;
				else cout<<" "<<allWeibos[W-1-j]->id;
			}
		} else {
			for(j=W/2-1; j>=0; --j) HeapAdjust(allWeibos, j, W);

			map<int, Topic*> searchedTopics;
			for(j=W-1; j>0; --j)
			{
				TopicLinkList* list = allWeibos[0]->topics;
				while( list != NULL )
				{
					int topicId = list->value->id;
					if( searchedTopics[topicId] == NULL ) 
					{
						list->value->weight = allWeibos[0]->weight;
						searchedTopics[topicId] = list->value;
					}
					list = list->pre;
				}

				if( searchedTopics.size() >= query->resultCount ) break;
				else {
					Weibo* tmp = allWeibos[j];
					allWeibos[j] = allWeibos[0];
					allWeibos[0] = tmp;
					HeapAdjust(allWeibos, 0, j);
				}
			}
			
			if( searchedTopics.size() > 0 )
			{
				Topic** topicList = new Topic*[searchedTopics.size()];
				map<int,Topic*>::iterator it;
				for(it=searchedTopics.begin(), j=0; it!=searchedTopics.end(); ++it, ++j) topicList[j] = it->second;
				QuickSort(topicList, 0, searchedTopics.size()-1);
				for(j=0; j<query->resultCount && j<searchedTopics.size(); j++)
				{
					if(j==0) cout<<topicList[j]->id;
					else cout<<" "<<topicList[j]->id;
				}
				delete[] topicList;
			} else cout<<"-1";
		}
		cout<<endl;
	}

	// Release memory
	for(i=0; i<W; i++)
	{
		TopicLinkList* topic = allWeibos[i]->topics;
		while( topic != NULL )
		{
			TopicLinkList* pre = topic->pre;
			topic->value = NULL;
			topic->pre = NULL;
			delete topic;
			topic = pre;
		}
		delete allWeibos[i];
	}
	delete[] allWeibos;

	for(i=0; i<T; i++) delete allTopics[i];
	delete[] allTopics;

	for(i=0; i<Q; i++) delete allQueries[i];
	delete[] allQueries;

	return 0;
}