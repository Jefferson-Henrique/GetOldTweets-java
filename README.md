# Get Old Tweets Programatically
A project written in Java to get old tweets, it bypass some limitations of Twitter Official API.

## Details
Twitter Official API has the bother limitation of time constraints, you can't get older tweets than a week. Some tools provide access to older tweets but in the most of them you have to spend some money before.
I was searching other tools to do this job but I didn't found it, so after analyze how Twitter Search of Browse works I understand it flow. Basically when you enter on Twitter page an scroll loader starts, if you scroll down you start to get more and more tweets, all through calls to a JSON response. I mimic this and so I get the best advaantage of Twitter Search on Browses, it can search the deep oldest tweets.

## Components
- Tweet: Model class to give some informations about a specific tweet (username, text, date, retweets, favorites)
- TweetManager: A manager class to help getting tweets in Tweet Model.
- Main: A simple class showing an example of use.
