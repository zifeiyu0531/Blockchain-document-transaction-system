# coding:utf-8
import jieba
import gensim
from gensim.models.doc2vec import Doc2Vec
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import json

TaggededDocument = gensim.models.doc2vec.TaggedDocument
MODEL_URL = './model/model_dm'


def train_text(doc):
    x_train = []
    # 如果是已经分好词的，不用再进行分词，直接按空格切分即可
    seg_list = jieba.cut(doc)
    word_list = (' '.join(jieba.cut(doc))).split(' ')
    l = len(word_list)
    word_list[l - 1] = word_list[l - 1].strip()
    document = TaggededDocument(word_list, tags=[0])
    x_train.append(document)
    print(x_train)

    train(x_train)


def train(x_train, size=100, epoch_num=1):  # size 是你最终训练出的句子向量的维度，自己尝试着修改一下

    model_dm = Doc2Vec(x_train, min_count=1, window=5,
                       size=size, sample=1e-3, negative=5, workers=4)
    model_dm.train(x_train, total_examples=model_dm.corpus_count, epochs=70)
    model_dm.save(MODEL_URL)  # 模型保存的位置

    return model_dm


def get_vec(doc):
    model_dm = Doc2Vec.load(MODEL_URL)
    # 此处需要读入你所需要进行提取出句子向量的文本   此处代码需要自己稍加修改一下
    # 你需要进行得到句子向量的文本，如果是分好词的，则不需要再调用结巴分词
    test_text = (' '.join(jieba.cut(doc))).split(' ')

    inferred_vector_dm = model_dm.infer_vector(test_text)  # 得到文本的向量
    sims = model_dm.docvecs.most_similar([inferred_vector_dm], topn=10)
    print(inferred_vector_dm)
    print(type(inferred_vector_dm))
    return inferred_vector_dm


def check_dup(vec_str1, vec_str2):
    vec_str1 = str_process(vec_str1)
    vec_str2 = str_process(vec_str2)
    print(vec_str1, vec_str2)
    vec_json1 = json.loads(vec_str1)
    vec_json2 = json.loads(vec_str2)
    vec1 = np.array(vec_json1)
    vec2 = np.array(vec_json2)
    return cosine_similarity(vec1.reshape(1, -1), vec2.reshape(1, -1))


def str_process(s):
    arr = s.split(" ")
    rtn = ''
    for i in range(len(arr)-1):
        rtn += str(arr[i]) + ', '
    rtn += str(arr[len(arr)-1])
    return rtn
