#!/usr/bin/python
# -*- coding: utf-8 -*-
# Author: Cui Yingjie (cuiyingjie@gmail.com)

import sys
import random
from nearby_map import *

def GenTopicIDs( topic_count, count):
    ids = [] 
    id_map = {}
    for i in range( count ):
        while True:
            id = random.randrange( 0 , topic_count )
            if not id in id_map:
                break
        ids.append( id )
        id_map[ id ] = 1
        
    return sorted( ids )
        
if __name__ == "__main__":
    if len(sys.argv) < 2:
        print "usage: nearby_testdata_generator.py test_case_file_name"
        exit(0)

    weibo_count = 10000
    topic_count = 1000
    query_count = 10000
    query_limit = 100
    aWeibo = []
    aTopic = []
    aQuery = []
    with open( sys.argv[ 1 ], "wt" ) as testfile:
        testfile.write( "%d %d %d\n" % ( weibo_count, topic_count, query_count ) )
        aTopicWeiBo = [] 
        
        for i in xrange( topic_count ):
            aTopicWeiBo.append( {} )
        
        for i in xrange( weibo_count ):
            wb = Weibo( i, GeoLocation( random.randrange( -18000 , 18000 ) / 100.0
                        , random.randrange( -9000 , 9000 ) / 100.0 ) )
            topics_id = GenTopicIDs( topic_count, random.randrange( 1 , 11 ) ) ;
            
            #print "%d %s" % ( i , " ".join( map( lambda w: str(w) , topics_id ) ) )
            for id in topics_id:
                aTopicWeiBo[ id ][ i ] = 1
                
            aWeibo.append( wb )
            testfile.write( "%d %.2f %.2f\n" % ( wb.id, wb.loc.lon, wb.loc.lat ) )
            
        for i in xrange( topic_count ):
            weibos_id = aTopicWeiBo[ i ].keys()
            #print "%d %s" % ( i , " ".join( map( lambda w: str(w) , weibos_id ) ) )
            topic = Topic( i , weibos_id )
            aTopic.append( topic )
            testfile.write( "%d %d %s\n" % ( topic.id , len( weibos_id )
                                             , " ".join( map( lambda w: str(w) , weibos_id ) ) ) )
            
            for id in weibos_id:
                aWeibo[ id ].topics.append( topic )

        for i in xrange( query_count ):
            query = Query(  '0' if i % 2 == 0 else '1', random.randrange( 1 , query_limit + 1 )
                    , GeoLocation( random.randrange( -18000 , 18000 ) / 100.0 , random.randrange( -9000 , 9000 ) / 100.0 ) )
            aQuery.append( query )
            testfile.write( "%s %d %.2f %.2f\n" % ( query.type , query.count , query.loc.lon, query.loc.lat ) )


    #generator answer file
    with open( sys.argv[ 1 ] + ".result", "wt" ) as testfile, open( sys.argv[ 1 ] + ".resultx", "wt" ) as detailfile:
        count = 0
        for qry in aQuery:
            count += 1
            print count
            for w in aWeibo:
                w.dist = w.loc.distance( qry.loc )

            sorted_weibo = sorted( aWeibo )
            if not qry.is_topic_query():
                testfile.write( " ".join( map( lambda w: str(w.id), sorted_weibo[ :qry.count ] ) ) + "\n" )
                detailfile.write( " ".join( map( lambda w: str(w.id) + ":" + str( w.dist ) , sorted_weibo[ :qry.count ] ) ) + "\n" )
            else:
                result_topics = []
                result_topics_map = {}
                for w in sorted_weibo:
                    for t in w.topics:
                        if t.id in result_topics_map:
                            if t.dist > w.dist:
                                t.dist = w.dist
                            continue

                        t.dist = w.dist
                        result_topics_map[ t.id ] = t.dist
                        result_topics.append( t )

                result_topics = sorted( result_topics )
                testfile.write( " ".join( map( lambda t: str(t.id), result_topics[ :qry.count ] ) ) + "\n" )
                detailfile.write( " ".join( map( lambda t: str(t.id) + ":" + str( t.dist ), result_topics[ :qry.count ] ) ) + "\n" )






